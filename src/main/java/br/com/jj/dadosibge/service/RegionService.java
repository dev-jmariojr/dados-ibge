package br.com.jj.dadosibge.service;

import br.com.jj.dadosibge.dto.RegionParams;
import br.com.jj.dadosibge.model.Region;
import br.com.jj.dadosibge.repository.RegionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class RegionService {

    private final RegionRepository repository;

    public List<Region> find(RegionParams params) {
        if(params.isEmpty())
            return repository.findAll();

        if(params.id() > 0)
            return repository.findItemById(params.id())
                    .map(List::of)
                    .orElse(Collections.emptyList());
        else
            return repository.findItemByShortName(params.shortName())
                    .map(List::of)
                    .orElse(Collections.emptyList());
    }

    public Region findByCode(String code) {
        return repository.findById(code)
                .orElseThrow(()->new IllegalArgumentException("Região não encontrada"));
    }

    public Region save(Region value) {
        var optionalRegion = repository.findItemById(value.getId());
        if(optionalRegion.isPresent()){
            value.setCode(optionalRegion.get().getCode());
        }
        else{
            value.setDt(LocalDateTime.now());
            value.setCountStates(0);
        }

        return repository.save(value);
    }

    public Boolean delete(Integer value) {
        var optionalRegion = repository.findItemById(value);
        if(optionalRegion.isEmpty())
            return false;

        repository.deleteById(optionalRegion.get().getCode());
        return true;
    }

    public void deleteAll() {
        repository.deleteAll();
    }

}
