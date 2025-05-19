package br.com.italo.rabbitmqsyncconsumer.consumer;

import br.com.italo.rabbitmqsyncconsumer.dto.MessageInfoDTO;
import br.com.italo.rabbitmqsyncconsumer.enums.ClassTypeEnum;
import br.com.italo.rabbitmqsyncconsumer.enums.OperationTypeEnum;
import br.com.italo.rabbitmqsyncconsumer.service.SyncRouter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class QueueConsumer {

    @Autowired
    private SyncRouter syncRouter;

    public void consumerQueue(String queueContent, Map<String, Object> headers) {
        String classType = (String) headers.get("classType");
        String operationType = (String) headers.get("operationType");
        Long queueId = (Long) headers.get("queueId");
        String traceId = (String) headers.get("traceId");

        log.info("START - CLASS_TYPE - {}, OPERATION_TYPE - {}, QUEUE_ID - {}",
                classType, operationType, queueId);
        log.info("body - {}", queueContent);

        ClassTypeEnum classTypeEnum = ClassTypeEnum.getClassType(classType);
        OperationTypeEnum operationTypeEnum = OperationTypeEnum.valueOf(operationType);
        MessageInfoDTO messageInfoDTO = new MessageInfoDTO();
        messageInfoDTO.setQueueId(queueId);
        messageInfoDTO.setClassTypeEnum(classTypeEnum);
        messageInfoDTO.setTraceId(traceId);
        messageInfoDTO.setOperationTypeEnum(operationTypeEnum);

        syncRouter.routerQueue(queueContent, messageInfoDTO);

        log.info("FINISH - CLASS_TYPE - {}, OPERATION_TYPE - {}, QUEUE_ID - {}",
                classType, operationType, queueId);
    }

}
