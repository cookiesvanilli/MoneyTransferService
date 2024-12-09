package com.example.MoneyTransferService.repository;

import com.example.MoneyTransferService.model.Amount;
import com.example.MoneyTransferService.model.Card;
import com.example.MoneyTransferService.model.req.TransferRequest;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class CardRepository {
    private final Map<String, Card> cards = new ConcurrentHashMap<>();
    private final Map<String, TransferRequest> transfers = new ConcurrentHashMap<>();
    private final Map<String, String> codes = new ConcurrentHashMap<>();
    private final AtomicInteger operationID = new AtomicInteger();

    {
        final String cardNum1 = "1000000000000000";
        final String cardNum2 = "2000000000000000";
        final String cardNum3 = "3000000000000000";

        cards.put(cardNum1, new Card(cardNum1, "10/24",
                "111", new Amount(100_000, "RUB")));
        cards.put(cardNum2, new Card(cardNum2, "10/24",
                "222", new Amount(100_000, "RUB")));
        cards.put(cardNum3, new Card(cardNum3, "10/24",
                "333", new Amount(100_000, "RUB")));

    }

    public Card getCard(String cardNumber) {
        return cards.get(cardNumber);
    }

    public int incrementAndGetOperationID() {
        return operationID.incrementAndGet();
    }

    public void putTransfer(String id, TransferRequest transferRequest) {
        transfers.put(id, transferRequest);
    }

    public void putCodes(String id, String code) {
        codes.put(id, code);
    }

    public TransferRequest removeTransfer(String id) {
        return transfers.remove(id);
    }

    public String removeCode(String id) {
        return codes.remove(id);
    }

}
