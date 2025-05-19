package br.com.italo.rabbitmqsyncconsumer.enums;

import br.com.italo.rabbitmqsyncconsumer.dto.AppleDTO;
import br.com.italo.rabbitmqsyncconsumer.dto.LemonDTO;
import br.com.italo.rabbitmqsyncconsumer.dto.SyncDTO;
import br.com.italo.rabbitmqsyncconsumer.exceptions.NotFoundClassTypeNameException;
import br.com.italo.rabbitmqsyncconsumer.service.AppleService;
import br.com.italo.rabbitmqsyncconsumer.service.LemonService;
import br.com.italo.rabbitmqsyncconsumer.service.OrangeService;
import br.com.italo.rabbitmqsyncconsumer.service.SyncService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@AllArgsConstructor
public enum ClassTypeEnum {

    APPLE(AppleService.class, AppleDTO.class),
    LEMON(LemonService.class, LemonDTO.class),
    ORANGE(OrangeService.class, LemonDTO.class);

    private final Class<? extends SyncService<? extends  SyncDTO>> service;
    private final Class<? extends  SyncDTO> dto;

    public static ClassTypeEnum getClassType(String name) {
        try {
            return ClassTypeEnum.valueOf(name);
        } catch (IllegalArgumentException e) {
            log.error("CLASS_TYPE not found - {}", name);
            throw new NotFoundClassTypeNameException(e);
        }
    }

}
