package br.com.italo.rabbitmqsyncconsumer.service;

import br.com.italo.rabbitmqsyncconsumer.dto.MessageInfoDTO;
import br.com.italo.rabbitmqsyncconsumer.dto.SyncDTO;
import br.com.italo.rabbitmqsyncconsumer.enums.ClassTypeEnum;
import br.com.italo.rabbitmqsyncconsumer.enums.OperationTypeEnum;
import br.com.italo.rabbitmqsyncconsumer.exceptions.InvalidSyntaxException;
import br.com.italo.rabbitmqsyncconsumer.exceptions.SyncMethodNotImplementedException;
import br.com.italo.rabbitmqsyncconsumer.exceptions.WrongClassTypeException;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class SyncRouter {

    private final List<SyncService<? extends SyncDTO>> syncServiceList;
    private final Gson gson;

    @Autowired
    public SyncRouter(List<SyncService<? extends SyncDTO>> syncServiceList, Gson gson) {
        this.syncServiceList = syncServiceList;
        this.gson = gson;
    }

    public void routerQueue(String queueContent, MessageInfoDTO messageInfoDTO) {
        ClassTypeEnum classTypeEnum = messageInfoDTO.getClassTypeEnum();
        SyncService<? extends SyncDTO> executeService = getServiceInstance(classTypeEnum)
                .orElseThrow(() -> new IllegalArgumentException("Service not found for: " + classTypeEnum));

        Object data = getObject(queueContent, classTypeEnum);
        invokeMethod(executeService, data, messageInfoDTO);
    }

    private Object getObject(String queueContent, ClassTypeEnum classTypeEnum) {
        try {
            Object data = isList(queueContent) ? parseList(queueContent, classTypeEnum.getDto()) : parseObject(queueContent, classTypeEnum.getDto());
            return data;
        } catch (JsonParseException e) {
            log.error("Erro ao desserializar o JSON - {} - CLASS_TYPE - {} ", queueContent, classTypeEnum.name());
            throw new InvalidSyntaxException(e);
        }
    }

    private Optional<SyncService<? extends SyncDTO>> getServiceInstance(ClassTypeEnum classTypeEnum) {
        return syncServiceList.stream()
                .filter(syncService -> classTypeEnum.getService().isAssignableFrom(syncService.getClass()))
                .findAny();
    }

    private <D> Object parseObject(String json, Class<D> clazz) {
        return gson.fromJson(json, clazz);
    }

    private <D> List<D> parseList(String jsonArray, Class<D> clazz) {
        Type typeOfT = TypeToken.getParameterized(List.class, clazz).getType();
        return gson.fromJson(jsonArray, typeOfT);
    }

    private <D extends SyncDTO> void invokeMethod(SyncService<?> service, Object data, MessageInfoDTO messageInfoDTO) {
        SyncService<D> typedService = (SyncService<D>) service;
        OperationTypeEnum operationTypeEnum = messageInfoDTO.getOperationTypeEnum();

        try {
            if (data instanceof List) {
                List<D> dtoList = (List<D>) data;
                switch (operationTypeEnum) {
                    case INSERT -> typedService.insert(dtoList, messageInfoDTO);
                    case UPDATE -> typedService.update(dtoList, messageInfoDTO);
                    case DELETE -> typedService.delete(dtoList, messageInfoDTO);
                    case EXECUTE -> typedService.execute(dtoList, messageInfoDTO);
                    default -> throw new IllegalArgumentException("Invalid operation type");
                }
            } else {
                D dto = (D) data;
                switch (operationTypeEnum) {
                    case INSERT -> typedService.insert(dto, messageInfoDTO);
                    case UPDATE -> typedService.update(dto, messageInfoDTO);
                    case DELETE -> typedService.delete(dto, messageInfoDTO);
                    case EXECUTE -> typedService.execute(dto, messageInfoDTO);
                    default -> throw new IllegalArgumentException("Invalid operation type");
                }
            }
        } catch (ClassCastException e) {
            log.error("Error ao executar o service {} - passando o DTO {}",
                    typedService.getClass().getSimpleName(), data.getClass().getSimpleName());
            throw new WrongClassTypeException(e);
        } catch (SyncMethodNotImplementedException e) {
            log.error("Método não implementado para o tipo {}, classe {}, service {} e parâmetro {}",
                    operationTypeEnum.name(), messageInfoDTO.getClassTypeEnum().name(),
                    typedService.getClass().getSimpleName(), data.getClass().getSimpleName());
            throw e;
        }
    }

    public static boolean isList(String json) {
        try {
            JsonElement jsonElement = new Gson().fromJson(json, JsonElement.class);
            return jsonElement.isJsonArray();
        } catch (JsonParseException e) {
            return false;
        }
    }

}