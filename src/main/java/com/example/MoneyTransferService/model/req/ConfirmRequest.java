package com.example.MoneyTransferService.model.req;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConfirmRequest {
    @NotBlank(message = "The operation number cannot be empty")
    @Pattern(regexp = "^[0-9]*$")
    private String operationId;
    @NotBlank(message = "The code should not be empty")
    private String code;


}