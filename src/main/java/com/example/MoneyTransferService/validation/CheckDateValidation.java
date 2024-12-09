package com.example.MoneyTransferService.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class CheckDateValidation implements ConstraintValidator<CheckDate, String> {
    private String minTillDate;

    @Override
    public void initialize(CheckDate checkTillDate) {

        minTillDate = DateTimeFormatter.ofPattern("MMyy")
                .format(LocalDateTime.now());
    }

    @Override
    public boolean isValid(String enteredValue,
                           ConstraintValidatorContext constraintValidatorContext) {
        String enter = enteredValue.substring(0, 1);
        return Integer.parseInt(enteredValue) > Integer.parseInt(minTillDate)
                && Integer.parseInt(enter) > 0 && Integer.parseInt(enter) <= 12;
    }
}
