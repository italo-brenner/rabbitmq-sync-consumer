package br.com.italo.rabbitmqsyncconsumer.service;

import br.com.italo.rabbitmqsyncconsumer.dto.MessageInfoDTO;
import br.com.italo.rabbitmqsyncconsumer.dto.OrangeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrangeService implements SyncService<OrangeDTO> {

    @Override
    public void execute(OrangeDTO dto, MessageInfoDTO messageInfoDTO) {
        log.info("Execute Orange: {}", dto);
    }

}
