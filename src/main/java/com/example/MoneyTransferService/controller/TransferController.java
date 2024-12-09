package com.example.MoneyTransferService.controller;

import com.example.MoneyTransferService.exception.InputDataException;
import com.example.MoneyTransferService.model.req.ConfirmRequest;
import com.example.MoneyTransferService.model.req.TransferRequest;
import com.example.MoneyTransferService.model.res.TransferConfirmResponse;
import com.example.MoneyTransferService.service.TransferService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin
public class TransferController {

    private final TransferService service;

    @PostMapping("/transfer")
    public TransferConfirmResponse postTransfer(@RequestBody TransferRequest transferRequest) throws InputDataException {
        return service.postTransfer(transferRequest);
    }

    @PostMapping("/confirmOperation")
    public TransferConfirmResponse postConfirmOperation(@RequestBody ConfirmRequest confirmRequest) throws InputDataException {
        return service.postConfirmOperation(confirmRequest);
    }
}
