package com.ericzzz.j8.lambda;

public class Validator {
    private final ValidationStrategy strategy;

    public Validator(ValidationStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean validate(String s) {
        return strategy.execute(s);
    }

    public static void main(String[] args) {
        Validator numericValidator = new Validator((s) -> s.matches("\\d+"));
        Validator lowerCaseValidator = new Validator((s) -> s.matches("[a-z]+"));
        System.out.println(numericValidator.validate("12345"));
        System.out.println(lowerCaseValidator.validate("abcde"));
    }
}
