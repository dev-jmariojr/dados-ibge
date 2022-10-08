package br.com.jj.dadosibge.controller;

import br.com.jj.dadosibge.model.Region;
import br.com.jj.dadosibge.service.RegionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/region")
public class RegionController {

    private final RegionService service;

    public RegionController(final RegionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Region>> regions(
            @RequestParam(required = false) Optional<String> shortName,
            @RequestParam(required = false) Optional<Integer> id)
    {
        String paramShortName = shortName.orElse("");
        Integer paramId = id.orElse(0);

        List<Region> list;
        if(paramShortName.isEmpty() && paramId.equals(0)) {
            list = service.findAll();
            if(list.isEmpty())
                return ResponseEntity.notFound().build();
            else
                return ResponseEntity.ok(list);
        }
        else {
            Region region = null;
            if(paramShortName.isEmpty()) {
                region = service.findById(paramId);
            }
            else {
                region = service.findByShortName(paramShortName);
            }

            if(region != null){
                list = Arrays.asList(region);
                return ResponseEntity.ok(list);
            }
            else {
                return ResponseEntity.notFound().build();
            }
        }
    }

}
