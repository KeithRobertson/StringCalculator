package com.keith.kata.stringcalculator.exception;

public class NegativeNumberException extends InvalidStringCalculatorArgumentException {

    public NegativeNumberException(String message) {
        super(message);
    }
}
