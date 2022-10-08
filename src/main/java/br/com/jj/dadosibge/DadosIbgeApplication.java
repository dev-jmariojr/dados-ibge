package br.com.jj.dadosibge;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableRabbit
@SpringBootApplication
@EnableMongoRepositories
public class DadosIbgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DadosIbgeApplication.class, args);
	}

}
