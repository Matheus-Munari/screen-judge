package com.filmes.avaliador.handler.exception.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class EnumValidator implements ConstraintValidator<EnumValid, String> {

    private Enum<?>[] valoresPermitidos;

    @Override
    public void initialize(EnumValid constraintAnnotation) {
        valoresPermitidos = constraintAnnotation.enumClass().getEnumConstants();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null) {
            return false; // Já será tratado pelo @NotNull se necessário
        }
        return Arrays.stream(valoresPermitidos)
                .anyMatch(e -> e.name().equals(value));

    }
}
