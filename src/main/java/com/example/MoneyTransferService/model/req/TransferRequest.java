package com.example.MoneyTransferService.model.req;

import com.example.MoneyTransferService.model.Amount;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransferRequest {
    @NotBlank(message = "Your card number is incorrect")
    @Size(min = 16, max = 16)
    @Pattern(regexp = "(?<!\\d)\\d{16}(?!\\d)")
    private String cardFromNumber;

    @NotBlank(message = "Incorrect expiration date of your card")
    @Size(min = 5, max = 5)
    @Pattern(regexp = "(0[1-9]|1[0-2])[/][0-9]{2}")
    private String cardFromValidTill;

    @NotBlank(message = "CVC2/CVV2 required for your card")
    @Size(min = 3, max = 3, message = "CVC2/CVV2 required for your card")
    private String cardFromCVV;

    @NotBlank(message = "The recipient's card number is incorrect")
    @Size(min = 16, max = 16)
    @Pattern(regexp = "(?<!\\d)\\d{16}(?!\\d)")
    private String cardToNumber;

    private Amount amount;
}
