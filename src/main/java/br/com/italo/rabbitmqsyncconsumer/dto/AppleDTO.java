package br.com.italo.rabbitmqsyncconsumer.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class AppleDTO implements SyncDTO {

    private String appleName;

}
