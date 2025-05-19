package br.com.italo.rabbitmqsyncconsumer.dto;

import br.com.italo.rabbitmqsyncconsumer.enums.ClassTypeEnum;
import br.com.italo.rabbitmqsyncconsumer.enums.OperationTypeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageInfoDTO {

    private Long queueId;

    private ClassTypeEnum classTypeEnum;

    private OperationTypeEnum operationTypeEnum;

    private String traceId;

}
