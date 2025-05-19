package br.com.italo.rabbitmqsyncconsumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class RabbitmqSyncConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqSyncConsumerApplication.class, args);
	}

}
