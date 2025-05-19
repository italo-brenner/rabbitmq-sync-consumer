package br.com.italo.rabbitmqsyncconsumer.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LemonDTO implements SyncDTO {

    private String lemonName;

}
