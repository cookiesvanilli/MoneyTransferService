package com.example.MoneyTransferService.service;

import com.example.MoneyTransferService.exception.InputDataException;
import com.example.MoneyTransferService.model.Card;
import com.example.MoneyTransferService.model.req.ConfirmRequest;
import com.example.MoneyTransferService.model.req.TransferRequest;
import com.example.MoneyTransferService.model.res.TransferConfirmResponse;
import com.example.MoneyTransferService.repository.CardRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@org.springframework.stereotype.Service
@AllArgsConstructor
@Slf4j
public class TransferService {
    private final CardRepository repository;

    public TransferConfirmResponse postTransfer(TransferRequest transferRequest) throws InputDataException {
        final Card cardFrom = repository.getCard(transferRequest.getCardFromNumber());
        final Card cardTo = repository.getCard(transferRequest.getCardToNumber());

        if (cardFrom == null && cardTo != null) {
            log.error("The sender's card {} was not found", cardFrom.getCardNumber());
            throw new InputDataException("The sender's card was not found");
        } else if (cardFrom != null && cardTo == null) {
            log.error("The recipient's card {} was not found", cardTo.getCardNumber());
            throw new InputDataException("The recipient's card was not found");
        } else if (cardFrom == null && cardTo == null) {
            log.error("The sender's {} and recipient's {} card were not found", cardFrom.getCardNumber(), cardTo.getCardNumber());
            throw new InputDataException("The sender's and recipient's card were not found");
        }

        if (cardFrom == cardTo) {
            log.error("Identical card numbers are indicated {} = {}", cardFrom.getCardNumber(), cardTo.getCardNumber());
            throw new InputDataException("The sender's card was not found");
        }

        final String cardFromValidTill = cardFrom.getCardValidTill();
        final String cardFromCVV = cardFrom.getCardCVV();
        final String transferRequestCardFromValidTill = transferRequest.getCardFromValidTill();
        final String transferRequestCardFromCVV = transferRequest.getCardFromCVV();

        if (!cardFromValidTill.equals(transferRequestCardFromValidTill) && cardFromCVV.equals(transferRequestCardFromCVV)) {
            log.info("The card expiration date is incorrect");
            throw new InputDataException("The card expiration date is incorrect");
        } else if (cardFromValidTill.equals(transferRequestCardFromValidTill) && !cardFromCVV.equals(transferRequestCardFromCVV)) {
            log.error("Incorrect CVV code is specified");
            throw new InputDataException("Incorrect CVV code is specified");
        } else if (!cardFromValidTill.equals(transferRequestCardFromValidTill) && !cardFromCVV.equals(transferRequestCardFromCVV)) {
            log.error("The expiration date and CVV code of the card are incorrect");
            throw new InputDataException("The expiration date and CVV code of the card are incorrect");
        }

        if (cardFrom.getAmount().getValue() < transferRequest.getAmount().getValue()) {
            log.error("Insufficient funds on the card {}", cardFrom.getCardNumber());
            throw new InputDataException("Insufficient funds on the card");
        } else if (cardFrom.getAmount().getValue() <= 0) {
            log.error("The amount must be more than {}", cardFrom.getAmount().getValue());
            throw new InputDataException("Insufficient funds on the card");
        }

        final String transferID = Integer.toString(repository.incrementAndGetOperationID());
        repository.putTransfer(transferID, transferRequest);
        repository.putCodes(transferID, "0000");

        return new TransferConfirmResponse(transferID);

    }

    public TransferConfirmResponse postConfirmOperation(ConfirmRequest confirmRequest) throws InputDataException {
        final String operationID = confirmRequest.getOperationId();

        final TransferRequest transferRequest = repository.removeTransfer(operationID);
        if (transferRequest == null) {
            log.error("The operation was not found");
            throw new InputDataException("The operation was not found");
        }

        final String code = repository.removeCode(operationID);
        if (!confirmRequest.getCode().equals(code) || code.isEmpty()) {
            log.error("Invalid code");
            throw new InputDataException("Invalid code");
        }

        final Card cardFrom = repository.getCard(transferRequest.getCardFromNumber());
        final Card cardTo = repository.getCard(transferRequest.getCardToNumber());

        final float cardFromAmount = cardFrom.getAmount().getValue();
        final float cardToAmount = cardTo.getAmount().getValue();
        final float transferAmount = transferRequest.getAmount().getValue();
        final float commission = (float) (transferAmount * 0.01);
        final String currency = transferRequest.getAmount().getCurrency();

        cardFrom.getAmount().setValue(cardFromAmount - transferAmount - commission);
        cardTo.getAmount().setValue(cardToAmount + transferAmount);

        log.info("The transfer was successfully completed. " +
                        "Operation number {}, sender's card number {}, recipient's card number {}, " +
                        "transfer amount {}, transfer currency {}, transfer fee {}", operationID, transferRequest.getCardFromNumber(),
                transferRequest.getCardToNumber(), transferAmount, currency, commission);

        return new TransferConfirmResponse(operationID);
    }

}
