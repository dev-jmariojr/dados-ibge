package br.com.jj.dadosibge.queue;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQSettings {

    @Value("${exchange.name}")
    private String exchangeName;

    @Value("${routingkey.regions}")
    private String routingKeyRegions;

    @Value("${routingkey.states}")
    private String routingKeyStates;

    @Value("${routingkey.cities}")
    private String routingKeyCities;

    @Bean
    DirectExchange exchange(){
        return new DirectExchange(exchangeName);
    }

    @Bean
    Queue queueRegions(){
        return new Queue("regions", true);
    }

    @Bean
    Queue queueStates(){
        return new Queue("states", true);
    }

    @Bean
    Queue queueCities(){
        return new Queue("cities", true);
    }

    @Bean
    Binding regionsBinding(DirectExchange exchange){
        return BindingBuilder.bind(queueRegions()).to(exchange).with(routingKeyRegions);
    }
    @Bean
    Binding statesBinding(DirectExchange exchange){
        return BindingBuilder.bind(queueStates()).to(exchange).with(routingKeyStates);
    }
    @Bean
    Binding citiesBinding(DirectExchange exchange){
        return BindingBuilder.bind(queueCities()).to(exchange).with(routingKeyCities);
    }


}
