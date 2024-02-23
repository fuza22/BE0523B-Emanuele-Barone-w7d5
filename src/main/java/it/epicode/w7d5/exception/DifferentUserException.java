package it.epicode.w7d5.exception;

public class DifferentUserException extends RuntimeException{

    public DifferentUserException(String message) {
        super(message);
    }
}
