package ru.nikituz.rest.utils.exceptions;

public class PersonCreateException  extends RuntimeException {
    public PersonCreateException(String message) {
        super(message);
    }
}
