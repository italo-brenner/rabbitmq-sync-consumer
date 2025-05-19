package br.com.italo.rabbitmqsyncconsumer.exceptions;

public class SyncMethodNotImplementedException extends UnsupportedOperationException {

    public SyncMethodNotImplementedException(String message) {
        super(message);
    }

}
