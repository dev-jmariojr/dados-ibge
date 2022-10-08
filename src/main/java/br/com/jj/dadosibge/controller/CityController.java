package br.com.jj.dadosibge.controller;

import br.com.jj.dadosibge.model.City;
import br.com.jj.dadosibge.service.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/city")
public class CityController {

    private final CityService service;

    public CityController(CityService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<City>> cities(
            @RequestParam(required = false) Optional<String> state,
            @RequestParam(required = false) Optional<String> name,
            @RequestParam(required = false) Optional<Integer> id)
    {
        String paramState = state.orElse("");
        String paramName = name.orElse("");
        Integer paramId = id.orElse(0);

        List<City> list = null;
        if(!paramState.isEmpty()){
            list = service.findItemsByState(paramState);
        }
        else if(paramId.equals(0)) {
            list = paramName.isEmpty()? service.findAll(): service.findByName(paramName);
        }
        else {
            City city = service.findById(paramId);
            if(city!=null)
                list = Arrays.asList(city);
        }

        if(list==null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(list);
    }
}
