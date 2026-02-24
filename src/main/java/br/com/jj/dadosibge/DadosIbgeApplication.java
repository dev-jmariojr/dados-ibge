package br.com.jj.dadosibge;

import br.com.jj.dadosibge.service.ImportService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableRabbit
@SpringBootApplication
@EnableMongoRepositories
@EnableFeignClients(basePackages = "br.com.jj.dadosibge.client")
public class DadosIbgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DadosIbgeApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(ImportService importService) {
		// servico para rodar a importação toda vez que a aplicação for iniciada
		return args -> importService.newImport();
	}

}
