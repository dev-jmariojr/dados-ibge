package br.com.jj.dadosibge.queue;

import br.com.jj.dadosibge.model.*;
import br.com.jj.dadosibge.service.CityService;
import br.com.jj.dadosibge.service.ImportService;
import br.com.jj.dadosibge.service.RegionService;
import br.com.jj.dadosibge.service.StateService;
import br.com.jj.dadosibge.utils.DateTimeDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class RabbitMQListener {

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new DateTimeDeserializer())
            .create();

    private final ImportService importService;
    private final RegionService regionService;
    private final StateService stateService;
    private final CityService cityService;

    protected String readMessageBody(Message message) {
        return new String(message.getBody(), StandardCharsets.UTF_8);
    }

    protected <T> T convertMessage(Object msg, Class<T> tClass) {
        Message message = (Message) msg;
        String body = readMessageBody(message);
        String bodyDecode = new String(Base64.getDecoder().decode(body), StandardCharsets.UTF_8);
        return GSON.fromJson(bodyDecode, tClass);
    }

    @RabbitListener(queues = {"regions"})
    public void receiveRegions(Message message) throws InterruptedException {
        Thread.sleep(1500);
        log.info("importar regioes");

        cityService.deleteAll();
        stateService.deleteAll();
        regionService.deleteAll();

        List<Region> regions = importService.regionList();
        for (Region region: regions) {
            Region regionSaved = regionService.save(region);
            log.info("regiao={}", regionSaved.getName());

            importService.producerMessage(regionSaved);
        }

    }

    @RabbitListener(queues = {"states"})
    public void receiveStates(Message message) throws InterruptedException {
        Thread.sleep(1500);
        log.info("importar estados");
        Region region = this.convertMessage(message, Region.class);
        if(region == null)
            return;

        List<State> states = this.importService.stateList(region);
        if(states.isEmpty())
            return;

        for (var state: states) {
            state.setRegion(region);
            state.setShortNameRegion(region.getShortName());
            State stateSaved = stateService.save(state);
            log.info("uf={} regiao={}", stateSaved.getShortName(), region.getName());

            importService.producerMessage(stateSaved);
        }

        region.setCountStates(states.size());
        regionService.save(region);
    }

    @RabbitListener(queues = {"cities"})
    public void receiveCities(Message message) throws InterruptedException {
        Thread.sleep(1500);
        log.info("importar cidades");
        State state = convertMessage(message, State.class);
        if(state == null)
            return;

        List<City> cities = importService.cityList(state);
        if(cities.isEmpty())
            return;

        for (var city: cities) {
            city.setState(state);
            city.setShortNameState(state.getShortName());
            City citySaved = cityService.save(city);
            log.info("cidade={} uf={}", citySaved.getName(), state.getShortName());
        }

        state.setCountCities(cities.size());
        stateService.save(state);
    }
}
