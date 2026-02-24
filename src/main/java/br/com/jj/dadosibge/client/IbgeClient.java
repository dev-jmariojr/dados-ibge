package br.com.jj.dadosibge.client;

import br.com.jj.dadosibge.model.City;
import br.com.jj.dadosibge.model.Region;
import br.com.jj.dadosibge.model.State;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
        name = "ibge-api",
        url = "${spring.api.ibge.urlbase}"
)
public interface IbgeClient {

    @GetMapping("${spring.api.ibge.path.regions}")
    ResponseEntity<List<Region>> getRegions();

    @GetMapping("${spring.api.ibge.path.states}")
    ResponseEntity<List<State>> getStates(@PathVariable String macrorregiao);

    @GetMapping("${spring.api.ibge.path.cities}")
    ResponseEntity<List<City>> getCities(@PathVariable String uf);

}
