package br.com.jj.dadosibge.controller;

import br.com.jj.dadosibge.dto.RegionParams;
import br.com.jj.dadosibge.model.Region;
import br.com.jj.dadosibge.service.RegionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/region")
public class RegionController {

    private final RegionService service;

    public RegionController(final RegionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Region>> findRegions(
            @RequestParam(required = false) String shortName,
            @RequestParam(required = false) Integer id)
    {
        var params = new RegionParams(shortName, id);
        List<Region> regions = service.find(params);
        if(regions.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(regions);

    }

}
