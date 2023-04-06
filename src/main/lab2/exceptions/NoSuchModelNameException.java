package main.lab2.exceptions;

public class NoSuchModelNameException extends Exception { //создали класс который расширяет существующий класс ошибок
    public NoSuchModelNameException(String message){ //задаем конструктор для этого класса
        super("Non existing model name: " + message); //задаем текст ошибки
    }
}
