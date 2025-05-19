package br.com.italo.rabbitmqsyncconsumer.enums;

import br.com.italo.rabbitmqsyncconsumer.exceptions.NotFoundOperationTypeNameException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum OperationTypeEnum {
    INSERT,
    UPDATE,
    DELETE,
    EXECUTE;

    public static OperationTypeEnum getOperationType(String name) {
        try {
            return OperationTypeEnum.valueOf(name);
        } catch (IllegalArgumentException e) {
            log.error("OPERATION_TYPE not found - {}", name);
            throw new NotFoundOperationTypeNameException(e);
        }
    }

}
