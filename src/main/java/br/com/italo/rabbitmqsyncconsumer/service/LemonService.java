package br.com.italo.rabbitmqsyncconsumer.service;

import br.com.italo.rabbitmqsyncconsumer.dto.LemonDTO;
import br.com.italo.rabbitmqsyncconsumer.dto.MessageInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class LemonService implements SyncService<LemonDTO> {

    @Override
    public void insert(List<LemonDTO> dto, MessageInfoDTO messageInfoDTO) {
        log.info("Insert List Lemon: {}", dto);
    }

    @Override
    public void update(LemonDTO dto, MessageInfoDTO messageInfoDTO) {
        log.info("Update Lemon: {}", dto);
    }

    @Override
    public void delete(LemonDTO dto, MessageInfoDTO messageInfoDTO) {
        log.info("Delete Lemon: {}", dto);
    }

}
