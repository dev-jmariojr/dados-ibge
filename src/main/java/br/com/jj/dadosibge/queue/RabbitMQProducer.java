package br.com.jj.dadosibge.queue;

import br.com.jj.dadosibge.model.ImportIBGE;
import br.com.jj.dadosibge.model.Region;
import br.com.jj.dadosibge.model.State;
import br.com.jj.dadosibge.utils.DateTimeSerializer;
import com.google.gson.*;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;

@Component
public class RabbitMQProducer {

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new DateTimeSerializer())
            .create();

    private final RabbitTemplate rabbitTemplate;

    private final Exchange exchange;

    public RabbitMQProducer(final RabbitTemplate rabbitTemplate, final Exchange exchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
    }

    public void publish(byte[] message, String routingKey){
        byte[] bytesEncoded = Base64.getEncoder().encode(message);
        rabbitTemplate.convertAndSend(this.exchange.getName(), routingKey, bytesEncoded);
    }

    public static byte[] message(ImportIBGE value){
        return GSON.toJson(value).getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] message(Region value){
        return GSON.toJson(value).getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] message(State value){
        return GSON.toJson(value).getBytes(StandardCharsets.UTF_8);
    }
}
