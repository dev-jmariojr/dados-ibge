package br.com.jj.dadosibge.service;

import br.com.jj.dadosibge.client.IbgeClient;
import br.com.jj.dadosibge.model.*;
import br.com.jj.dadosibge.queue.RabbitMQProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class ImportService {

    @Value("${routingkey.regions}")
    private String routingKeyRegions;

    @Value("${routingkey.states}")
    private String routingKeyStates;

    @Value("${routingkey.cities}")
    private String routingKeyCities;

    private final RabbitMQProducer producer;
    private final IbgeClient ibgeClient;

    public ImportService(RabbitMQProducer producer, IbgeClient ibgeClient) {
        this.producer = producer;
        this.ibgeClient = ibgeClient;
    }

    public void newImport() {
        producer.publish(RabbitMQProducer.message("new import dados"), routingKeyRegions);
    }

    public void producerMessage(Object message) {
        if(Objects.isNull(message))
            return;

        if(message instanceof Region)
            producerMessage(routingKeyStates, message);
        else if(message instanceof State)
            producerMessage(routingKeyCities, message);
    }

    private void producerMessage(String routingKey, Object message) {
        producer.publish(RabbitMQProducer.message(message), routingKey);
    }

    public List<Region> regionList() {
        var response = ibgeClient.getRegions();
        if(!response.getStatusCode().is2xxSuccessful())
            log.warn("Erro ao obter regioes do IBGE: {}", response.getStatusCode());

        return response.getBody();
    }

    public List<State> stateList(Region value) {
        var response = ibgeClient.getStates(value.getShortName());
        if(!response.getStatusCode().is2xxSuccessful())
            log.warn("Erro ao obter estados do IBGE: {}", response.getStatusCode());

        return response.getBody();
    }

    public List<City> cityList(State value) {
        var response = ibgeClient.getCities(value.getShortName());
        if(!response.getStatusCode().is2xxSuccessful())
            log.warn("Erro ao obter cidades do IBGE: {}", response.getStatusCode());

        return response.getBody();
    }

}
