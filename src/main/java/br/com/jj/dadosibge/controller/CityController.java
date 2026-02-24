package br.com.jj.dadosibge.controller;

import br.com.jj.dadosibge.dto.CityParams;
import br.com.jj.dadosibge.model.City;
import br.com.jj.dadosibge.service.CityService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/city")
@AllArgsConstructor
public class CityController {

    private final CityService service;

    @GetMapping
    public ResponseEntity<List<City>> cities(
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer id)
    {
        var params = new CityParams(state, name, id);
        List<City> cities = service.find(params);
        if(cities.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(cities);
    }
}
