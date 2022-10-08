package br.com.jj.dadosibge.service;

import br.com.jj.dadosibge.model.City;
import br.com.jj.dadosibge.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository repository;

    public CityServiceImpl(CityRepository repository) {
        this.repository = repository;
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public List<City> findAll() {
        return repository.findAll();
    }

    @Override
    public List<City> findItemsByState(String value) {
        return repository.findItemsByState(value);
    }

    @Override
    public City findByCode(String value) {
        return repository.findById(value)
                .orElseThrow(()-> new IllegalArgumentException("Cidade não existe"));
    }

    @Override
    public City findById(Integer value) {
        return repository.findItemById(value);
    }

    @Override
    public List<City> findByName(String value) {
        return repository.findItemByName(String.format("^%s", value));
    }

    @Override
    public City save(City value) {
        City city = null;
        try{
            value.setShortNameState(value.getState().getShortName());
            value.setDt(LocalDateTime.now());
            city=repository.save(value);
        }catch (Exception e){
            e.printStackTrace();
        }
        return city;
    }
}
