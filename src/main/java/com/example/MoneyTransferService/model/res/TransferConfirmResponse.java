package com.example.MoneyTransferService.model.res;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferConfirmResponse {
    @NotBlank(message = "The operation number cannot be let")
    @Pattern(regexp = "^[0-9]*$")
    private String operationId;
}
