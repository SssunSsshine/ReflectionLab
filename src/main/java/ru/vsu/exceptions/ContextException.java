package ru.vsu.exceptions;

/*
 Ошибка при построении контекста
 */
public class ContextException extends RuntimeException{
    public ContextException(String message) {
        super(message);
    }
}
