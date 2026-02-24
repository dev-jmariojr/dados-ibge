package br.com.jj.dadosibge.service;

import br.com.jj.dadosibge.dto.StateParams;
import br.com.jj.dadosibge.model.State;
import br.com.jj.dadosibge.repository.StateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class StateService {

    private final StateRepository repository;

    public List<State> find(StateParams params) {
        if(params.isEmpty())
            return repository.findAll();

        if(params.id() >0)
            return repository.findItemById(params.id())
                    .map(List::of)
                    .orElse(Collections.emptyList());
        else if (params.shortName() != null)
            return repository.findItemByShortName(params.shortName())
                    .map(Collections::singletonList)
                    .orElse(Collections.emptyList());
        else
            return repository.findItemsByShortNameRegion(params.shortNameRegion());
    }

    public State findByCode(String value) {
        return repository.findById(value)
                .orElseThrow(()-> new IllegalArgumentException("Estado não existe"));
    }

    public State save(State value) {
        var optionalState = repository.findItemById(value.getId());
        if(optionalState.isPresent()){
            value.setCode(optionalState.get().getCode());
        }
        else {
            value.setDt(LocalDateTime.now());
            value.setCountCities(0);
            value.setShortNameRegion(value.getRegion().getShortName());
        }

        return repository.save(value);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
