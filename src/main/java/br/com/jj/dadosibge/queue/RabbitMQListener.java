package br.com.jj.dadosibge.queue;

import br.com.jj.dadosibge.model.*;
import br.com.jj.dadosibge.service.CityService;
import br.com.jj.dadosibge.service.ImportService;
import br.com.jj.dadosibge.service.RegionService;
import br.com.jj.dadosibge.service.StateService;
import br.com.jj.dadosibge.utils.DateTimeDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

@Component
public class RabbitMQListener {

    @Value("${routingkey.states}")
    private String routingKeyStates;

    @Value("${routingkey.cities}")
    private String routingKeyCities;

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new DateTimeDeserializer())
            .create();

    private final ImportService importService;
    private final RegionService regionService;
    private final StateService stateService;
    private final CityService cityService;

    private final RabbitMQProducer producer;

    public RabbitMQListener(ImportService importService, RegionService regionService, StateService stateService, CityService cityService, RabbitMQProducer producer) {
        this.importService = importService;
        this.regionService = regionService;
        this.stateService = stateService;
        this.cityService = cityService;
        this.producer = producer;
    }

    protected String readMessageBody(Message message) throws UnsupportedEncodingException{
        return new String(message.getBody(), StandardCharsets.UTF_8);
    }

    protected <T> T convertMessage(Object msg, Class<T> tClass) throws UnsupportedEncodingException {
        Message message = (Message) msg;
        String body = readMessageBody(message);
        String bodyDecode = new String(Base64.getDecoder().decode(body), StandardCharsets.UTF_8);
        return GSON.fromJson(bodyDecode, tClass);
    }

    @RabbitListener(queues = {"regions"})
    public void receiveRegions(Message message) throws UnsupportedEncodingException {
        String routingKey = message.getMessageProperties().getReceivedRoutingKey().toUpperCase();
        System.out.println(routingKey);

        ImportIBGE importIBGE = this.convertMessage(message, ImportIBGE.class);
        if(importIBGE.getActive().equals(ImportIBGEActive.IMPORTANDO.getId()))
        {
            cityService.deleteAll();
            stateService.deleteAll();
            regionService.deleteAll();
            List<Region> list = this.importService.regionList();
            if(list!=null){
                for (int i = 0; i < list.size(); i++) {
                    Region region = regionService.save(list.get(i));
                    producer.publish(RabbitMQProducer.message(region), routingKeyStates);
                }
            }
        }
    }

    @RabbitListener(queues = {"states"})
    public void receiveStates(Message message) throws UnsupportedEncodingException {
        String routingKey = message.getMessageProperties().getReceivedRoutingKey().toUpperCase();
        System.out.println(routingKey);

        Region region = this.convertMessage(message, Region.class);
        if(region!=null)
        {
            List<State> list = this.importService.stateList(region);
            if(list!=null){
                region.setCountStates(list.size());
                regionService.save(region);
                for (int i = 0; i < list.size(); i++) {
                    State state = stateService.save(list.get(i));
                    producer.publish(RabbitMQProducer.message(state), routingKeyCities);
                }
            }
        }
    }

    @RabbitListener(queues = {"cities"})
    public void receiveCities(Message message) throws UnsupportedEncodingException {
        String routingKey = message.getMessageProperties().getReceivedRoutingKey().toUpperCase();
        System.out.println(routingKey);

        State state = this.convertMessage(message, State.class);
        if(state!=null)
        {
            List<City> list = this.importService.cityList(state);
            if(list!=null){
                state.setCountCities(list.size());
                stateService.save(state);
                for (int i = 0; i < list.size(); i++) {
                    City city = cityService.save(list.get(i));
                    System.out.println(city.getName());
                }
            }
        }
    }
}
