package com.example.MoneyTransferService.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckDateValidation.class)
public @interface CheckDate {
    public String message() default "Incorrect expiration date of your card";

    public Class<?>[] groups() default {};
    public Class<? extends Payload> [] payload() default {};
}
