package br.com.jj.dadosibge.service;

import br.com.jj.dadosibge.model.Region;
import br.com.jj.dadosibge.queue.RabbitMQProducer;
import br.com.jj.dadosibge.repository.RegionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RegionServiceImpl implements RegionService {

    private final RegionRepository repository;

    public RegionServiceImpl(
            final RegionRepository repository)
    {
        this.repository = repository;
    }

    @Override
    public Region findById(Integer value) {
        return this.repository.findItemById(value);
    }

    @Override
    public Region findByShortName(String value) {
        return this.repository.findItemByShortName(value);
    }

    @Override
    public Region findByCode(String code) {
        return this.repository.findById(code)
                .orElseThrow(()->new IllegalArgumentException("Região não encontrada"));
    }

    @Override
    public Region save(Region value) {
        Region find = this.repository.findItemById(value.getId());
        if(find!=null){
            value.setCode(find.getCode());
        }
        else{
            value.setDt(LocalDateTime.now());
            value.setCountStates(0);
        }

        return this.repository.save(value);
    }

    @Override
    public Boolean delete(Integer value) {
        try{
            Region region = this.repository.findItemById(value);
            if(region==null)
                return false;
            else {
                this.repository.deleteById(region.getCode());
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public List<Region> findAll() {
        return this.repository.findAll();
    }
}
