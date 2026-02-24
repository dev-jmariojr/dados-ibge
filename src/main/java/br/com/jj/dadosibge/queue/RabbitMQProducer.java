package br.com.jj.dadosibge.queue;

import br.com.jj.dadosibge.utils.DateTimeSerializer;
import com.google.gson.*;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;

@Component
@AllArgsConstructor
public class RabbitMQProducer {

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new DateTimeSerializer())
            .create();

    private final RabbitTemplate rabbitTemplate;
    private final Exchange exchange;

    public void publish(byte[] message, String routingKey){
        byte[] bytesEncoded = Base64.getEncoder().encode(message);
        rabbitTemplate.convertAndSend(this.exchange.getName(), routingKey, bytesEncoded);
    }

    public static byte[] message(Object value){
        return GSON.toJson(value).getBytes(StandardCharsets.UTF_8);
    }
}
