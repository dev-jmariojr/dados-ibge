package br.com.jj.dadosibge.service;

import br.com.jj.dadosibge.model.*;
import br.com.jj.dadosibge.queue.RabbitMQProducer;
import br.com.jj.dadosibge.repository.ImportRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ImportServiceImpl implements ImportService {

    @Value("${routingkey.regions}")
    private String routingKeyRegions;

    @Value("${spring.api.ibge.url}")
    private String urlBase;

    private static String pathRegions = "regioes";
    private static String pathStates = "regioes/{macrorregiao}/estados";
    private static String pathCities = "estados/{UF}/municipios";
    private final RabbitMQProducer producer;
    private final ImportRepository repository;

    public ImportServiceImpl(RabbitMQProducer producer, ImportRepository repository) {
        this.producer = producer;
        this.repository = repository;
    }

    @Override
    public Boolean existsActive() {
        ImportIBGE i = repository.findActive(ImportIBGEActive.IMPORTANDO.getId());
        return i!=null;
    }

    @Override
    public Boolean newImport() {
        if(this.existsActive())
            return false;

        ImportIBGE value = ImportIBGE.builder()
                .dt(LocalDateTime.now())
                .active(ImportIBGEActive.IMPORTANDO.getId())
                .build();

        value = repository.save(value);
        producer.publish(RabbitMQProducer.message(value), routingKeyRegions);

        return !value.getId().isEmpty();
    }

    @Override
    public List<ImportIBGE> allImport() {
        return repository.findAll();
    }

    @Override
    public ImportIBGE findById(String value) {
        return repository.findById(value)
                .orElseThrow(()-> new IllegalArgumentException("Erro ao buscar dados import IBGE"));
    }

    @Override
    public Boolean abortImport(String value) {
        repository.deleteAll();
        ImportIBGE importIBGE = null;
        try{
            importIBGE = this.findById(value);
            if(importIBGE!=null){
                importIBGE.setActive(ImportIBGEActive.ABORTADO.getId());
                repository.save(importIBGE);
            }
        }catch (Exception e){
            e.printStackTrace();
            importIBGE = null;
        }
        return importIBGE!=null;
    }

    @Override
    public List<Region> regionList() {
        ResponseEntity<List<Region>> response = null;
        try {
            RestTemplate template = new RestTemplate();
            response = template.exchange(
                    String.format("%s/%s", urlBase, pathRegions),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Region>>(){});

        }catch (Exception e){
            e.printStackTrace();
        }

        return response.getBody();
    }

    @Override
    public List<State> stateList(Region value) {
        List<State> list = null;
        ResponseEntity<List<State>> response = null;
        try {
            RestTemplate template = new RestTemplate();
            response = template.exchange(
                    String.format("%s/%s", urlBase, pathStates.replace("{macrorregiao}",value.getId().toString())),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<State>>(){});

            list = response.getBody();
            if(list!=null && !list.isEmpty()){
                for (State s: list) {
                    s.setRegion(value);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<City> cityList(State value) {
        List<City> list = null;
        ResponseEntity<List<City>> response = null;
        try {
            RestTemplate template = new RestTemplate();
            response = template.exchange(
                    String.format("%s/%s", urlBase, pathCities.replace("{UF}", value.getId().toString())),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<City>>(){});

            list = response.getBody();
            if(list!=null && !list.isEmpty()){
                for (City c: list) {
                    c.setState(value);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }

}
