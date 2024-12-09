package com.example.MoneyTransferService.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Amount {
    @NotBlank(message = "Invalid transfer amount specified")
    @Min(1)
    private float value;
    @NotBlank(message = "The currency must be specified")
    private String currency;

}
