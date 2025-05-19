package br.com.italo.rabbitmqsyncconsumer.service;

import br.com.italo.rabbitmqsyncconsumer.dto.MessageInfoDTO;
import br.com.italo.rabbitmqsyncconsumer.exceptions.SyncMethodNotImplementedException;

import java.util.List;

public interface SyncService<D> {

    default void insert(D dto, MessageInfoDTO messageInfoDTO) {
        throw new SyncMethodNotImplementedException("Method not found");
    }

    default void update(D dto, MessageInfoDTO messageInfoDTO) {
        throw new SyncMethodNotImplementedException("Method not found");
    }

    default void delete(D dto, MessageInfoDTO messageInfoDTO) {
        throw new SyncMethodNotImplementedException("Method not found");
    }

    default void execute(D dto, MessageInfoDTO messageInfoDTO) {
        throw new SyncMethodNotImplementedException("Method not found");
    }

    default void insert(List<D> dto, MessageInfoDTO messageInfoDTO) {
        throw new SyncMethodNotImplementedException("Method not found");
    }

    default void update(List<D> dto, MessageInfoDTO messageInfoDTO) {
        throw new SyncMethodNotImplementedException("Method not found");
    }

    default void delete(List<D> dto, MessageInfoDTO messageInfoDTO) {
        throw new SyncMethodNotImplementedException("Method not found");
    }

    default void execute(List<D> dto, MessageInfoDTO messageInfoDTO) {
        throw new SyncMethodNotImplementedException("Method not found");
    }

}
