package com.company.exceptions;

public class NoSuchModelNameException extends Exception {
    public NoSuchModelNameException(String message){
        super("No such model name: " + message);
    }
}