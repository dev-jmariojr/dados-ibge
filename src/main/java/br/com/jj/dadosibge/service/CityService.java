package br.com.jj.dadosibge.service;

import br.com.jj.dadosibge.dto.CityParams;
import br.com.jj.dadosibge.model.City;
import br.com.jj.dadosibge.repository.CityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class CityService {

    private final CityRepository repository;

    public List<City> find(CityParams params) {
        if(params == null || params.isEmpty())
            return repository.findAll();

        if(params.id()!= null && params.id() >0) {
            return repository.findItemById(params.id())
                    .map(Collections::singletonList)
                    .orElse(Collections.emptyList());
        }
        else if (params.state()!= null && !params.state().isEmpty()) {
            return repository.findItemsByState(params.state());
        }
        else
            return repository.findItemByName(String.format("^%s", params.name()));
    }

    public City save(City value) {
        value.setShortNameState(value.getState().getShortName());
        value.setDt(LocalDateTime.now());
        return repository.save(value);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

}
