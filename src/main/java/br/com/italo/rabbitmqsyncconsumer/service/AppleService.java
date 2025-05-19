package br.com.italo.rabbitmqsyncconsumer.service;

import br.com.italo.rabbitmqsyncconsumer.dto.AppleDTO;
import br.com.italo.rabbitmqsyncconsumer.dto.MessageInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AppleService implements SyncService<AppleDTO> {

    @Override
    public void insert(AppleDTO dto, MessageInfoDTO messageInfoDTO) {
        log.info("Insert Apple: {}", dto);
    }

    @Override
    public void update(AppleDTO dto, MessageInfoDTO messageInfoDTO) {
        log.info("Update Apple: {}", dto);
    }

    @Override
    public void delete(AppleDTO dto, MessageInfoDTO messageInfoDTO) {
        log.info("Delete Apple: {}", dto);
    }

}
