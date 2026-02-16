package com.javamoon.spring_algaworks.core.validation;

import java.math.BigDecimal;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MultipleValidator implements ConstraintValidator<Multiple, Number> {

    private int multipleNum;

    @Override
    public void initialize(Multiple constraintAnnotation) {
        multipleNum = constraintAnnotation.number();
    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext context) {
        boolean valid = true;

        if (value != null) {
            BigDecimal decimalValue = BigDecimal.valueOf(value.doubleValue());
            BigDecimal decimalMultiple = BigDecimal.valueOf(multipleNum);

            BigDecimal remainder = decimalValue.remainder(decimalMultiple);
            valid = BigDecimal.ZERO.compareTo(remainder) == 0;
        }

        return valid;
    }

}
