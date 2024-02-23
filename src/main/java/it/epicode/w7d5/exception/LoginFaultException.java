package it.epicode.w7d5.exception;

public class LoginFaultException extends RuntimeException{

    public LoginFaultException(String message){
        super(message);
    }
}
