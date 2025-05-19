package br.com.italo.rabbitmqsyncconsumer.service;

import br.com.italo.rabbitmqsyncconsumer.dto.MessageInfoDTO;
import br.com.italo.rabbitmqsyncconsumer.enums.ClassTypeEnum;
import br.com.italo.rabbitmqsyncconsumer.enums.OperationTypeEnum;
import br.com.italo.rabbitmqsyncconsumer.exceptions.InvalidSyntaxException;
import br.com.italo.rabbitmqsyncconsumer.exceptions.SyncMethodNotImplementedException;
import br.com.italo.rabbitmqsyncconsumer.exceptions.WrongClassTypeException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class SyncRouterTest {

    @Autowired
    private SyncRouter syncRouter;

    @Test
    void insertApple() {
        String appleStr = "{\"appleName\": \"AppleName\"}";

        MessageInfoDTO messageInfoDTO = new MessageInfoDTO();
        messageInfoDTO.setQueueId(1L);
        messageInfoDTO.setTraceId("12345");
        messageInfoDTO.setClassTypeEnum(ClassTypeEnum.APPLE);
        messageInfoDTO.setOperationTypeEnum(OperationTypeEnum.INSERT);
        syncRouter.routerQueue(appleStr, messageInfoDTO);
    }

    @Test
    void insertListLemon() {

        String lemonStrList = "[{\"lemonName\": \"LemonName\"}, {\"lemonName\": \"LemonName2\"}]";

        MessageInfoDTO messageInfoDTO = new MessageInfoDTO();
        messageInfoDTO.setQueueId(1L);
        messageInfoDTO.setTraceId("12345");
        messageInfoDTO.setClassTypeEnum(ClassTypeEnum.LEMON);
        messageInfoDTO.setOperationTypeEnum(OperationTypeEnum.INSERT);

        syncRouter.routerQueue(lemonStrList, messageInfoDTO);
    }

    @Test
    void executeOrangeWrongDTO() {

        String orangeStr = "{\"orangeName\": \"WrongDTO\"}";

        MessageInfoDTO messageInfoDTO = new MessageInfoDTO();
        messageInfoDTO.setQueueId(1L);
        messageInfoDTO.setTraceId("12345");
        messageInfoDTO.setClassTypeEnum(ClassTypeEnum.ORANGE);
        messageInfoDTO.setOperationTypeEnum(OperationTypeEnum.EXECUTE);

        WrongClassTypeException wrongClassTypeException = assertThrows(WrongClassTypeException.class,
                () -> syncRouter.routerQueue(orangeStr, messageInfoDTO));

        assertNotNull(wrongClassTypeException);
    }

    @Test
    void insertLemonSyncMethodNotImplemented() {

        String lemonStr = "{\"lemonName\": \"LemonName\"}";

        MessageInfoDTO messageInfoDTO = new MessageInfoDTO();
        messageInfoDTO.setQueueId(1L);
        messageInfoDTO.setTraceId("12345");
        messageInfoDTO.setClassTypeEnum(ClassTypeEnum.LEMON);
        messageInfoDTO.setOperationTypeEnum(OperationTypeEnum.INSERT);

        SyncMethodNotImplementedException syncMethodNotImplementedException = assertThrows(SyncMethodNotImplementedException.class,
                () -> syncRouter.routerQueue(lemonStr, messageInfoDTO));

        assertNotNull(syncMethodNotImplementedException);
    }

    @Test
    void updateLemonSyncMethodNotImplemented() {

        String lemonStrList = "[{\"lemonName\": \"LemonName\"}, {\"lemonName\": \"LemonName2\"}]";

        MessageInfoDTO messageInfoDTO = new MessageInfoDTO();
        messageInfoDTO.setQueueId(1L);
        messageInfoDTO.setTraceId("12345");
        messageInfoDTO.setClassTypeEnum(ClassTypeEnum.LEMON);
        messageInfoDTO.setOperationTypeEnum(OperationTypeEnum.UPDATE);

        SyncMethodNotImplementedException syncMethodNotImplementedException = assertThrows(SyncMethodNotImplementedException.class,
                () -> syncRouter.routerQueue(lemonStrList, messageInfoDTO));

        assertNotNull(syncMethodNotImplementedException);
    }

    @Test
    void parseException() {

        String lemonWrongStrList = "[{\"lemonName\": \"LemonName\", {\"lemonName\": \"LemonName2\"}]";

        MessageInfoDTO messageInfoDTO = new MessageInfoDTO();
        messageInfoDTO.setQueueId(1L);
        messageInfoDTO.setTraceId("12345");
        messageInfoDTO.setClassTypeEnum(ClassTypeEnum.LEMON);
        messageInfoDTO.setOperationTypeEnum(OperationTypeEnum.UPDATE);

        InvalidSyntaxException invalidSyntaxException = assertThrows(InvalidSyntaxException.class,
                () -> syncRouter.routerQueue(lemonWrongStrList, messageInfoDTO));

        assertNotNull(invalidSyntaxException);
    }

}
