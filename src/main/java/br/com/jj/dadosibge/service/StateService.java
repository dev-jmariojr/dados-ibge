package br.com.jj.dadosibge.service;

import br.com.jj.dadosibge.model.State;

import java.util.List;

public interface StateService {

    public void deleteAll();
    public List<State> findAll();
    public State findById(Integer value);
    public State findByShortName(String value);
    public State findByCode(String value);
    public List<State> findByRegion(String value);
    public State save(State value);

}
