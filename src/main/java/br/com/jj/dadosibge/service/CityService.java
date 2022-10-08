package br.com.jj.dadosibge.service;

import br.com.jj.dadosibge.model.City;

import java.util.List;

public interface CityService {

    public void deleteAll();
    public List<City> findAll();

    public List<City> findItemsByState(String value);

    public City findByCode(String value);

    public City findById(Integer value);

    public List<City> findByName(String value);

    public City save(City value);
}
