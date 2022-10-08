package br.com.jj.dadosibge.service;

import br.com.jj.dadosibge.model.State;
import br.com.jj.dadosibge.repository.StateRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StateServiceImpl implements StateService {

    private final StateRepository repository;

    public StateServiceImpl(final StateRepository repository) {
        this.repository = repository;
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public List<State> findAll() {
        return this.repository.findAll();
    }

    @Override
    public List<State> findByRegion(String value) {
        return this.repository.findItemsByShortNameRegion(value);
    }

    @Override
    public State findById(Integer value) {
        return this.repository.findItemById(value);
    }

    @Override
    public State findByShortName(String value) {
        return this.repository.findItemByShortName(value);
    }

    @Override
    public State findByCode(String value) {
        return this.repository.findById(value)
                .orElseThrow(()-> new IllegalArgumentException("Estado não existe"));
    }

    @Override
    public State save(State value) {
        State find = repository.findItemById(value.getId());
        if(find!=null){
            value.setCode(find.getCode());
        }
        else{
            value.setDt(LocalDateTime.now());
            value.setCountCities(0);
            value.setShortNameRegion(value.getRegion().getShortName());
        }

        return this.repository.save(value);
    }
}
