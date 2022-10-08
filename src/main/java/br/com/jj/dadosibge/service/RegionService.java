package br.com.jj.dadosibge.service;

import br.com.jj.dadosibge.model.Region;

import java.util.List;

public interface RegionService {

    public Region findById(Integer value);

    public Region findByShortName(String value);

    public Region findByCode(String value);

    public Region save(Region value);

    public Boolean delete(Integer value);

    public void deleteAll();

    public List<Region> findAll();

}
