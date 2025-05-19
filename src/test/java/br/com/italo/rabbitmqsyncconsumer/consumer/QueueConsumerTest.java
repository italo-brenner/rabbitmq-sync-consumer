package br.com.italo.rabbitmqsyncconsumer.consumer;

import br.com.italo.rabbitmqsyncconsumer.exceptions.NotFoundClassTypeNameException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class QueueConsumerTest {

    @Autowired
    private QueueConsumer syncRouter;

    @Test
    void insertApple() {

        String appleStr = "{\"appleName\": \"AppleName\"}";

        Map<String, Object> messageInfoMap = new HashMap<>();
        messageInfoMap.put("queueId", 1L);
        messageInfoMap.put("traceId", "12345");
        messageInfoMap.put("classType", "APPLE");
        messageInfoMap.put("operationType", "INSERT");

        syncRouter.consumerQueue(appleStr, messageInfoMap);
    }

    @Test
    void insertListLemon() {

        String appleStrList = "[{\"lemonName\": \"LemonName\"}, {\"lemonName\": \"LemonName2\"}]";

        Map<String, Object> messageInfoMap = new HashMap<>();
        messageInfoMap.put("queueId", 1L);
        messageInfoMap.put("traceId", "12345");
        messageInfoMap.put("classType", "LEMON");
        messageInfoMap.put("operationType", "INSERT");

        syncRouter.consumerQueue(appleStrList, messageInfoMap);
    }

    @Test
    void invalidClassTypeNameListLemon() {

        String appleStrList = "[{\"lemonName\": \"LemonName\"}, {\"lemonName\": \"LemonName2\"}]";

        Map<String, Object> messageInfoMap = new HashMap<>();
        messageInfoMap.put("queueId", 1L);
        messageInfoMap.put("traceId", "12345");
        messageInfoMap.put("classType", "BANANA");
        messageInfoMap.put("operationType", "INSERT");

        NotFoundClassTypeNameException notFoundClassTypeNameException = assertThrows(NotFoundClassTypeNameException.class,
                () -> syncRouter.consumerQueue(appleStrList, messageInfoMap));

        assertNotNull(notFoundClassTypeNameException);
    }

}
