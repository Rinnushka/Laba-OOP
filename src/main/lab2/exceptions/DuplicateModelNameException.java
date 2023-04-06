package main.lab2.exceptions;

public class DuplicateModelNameException extends Exception {
    public DuplicateModelNameException (String message){
        super("Duplicated Model name:" + message);
    }
}
