package br.com.italo.rabbitmqsyncconsumer.exceptions;

public class WrongClassTypeException extends RuntimeException {

    public WrongClassTypeException(Throwable cause) {
        super(cause);
    }

}
