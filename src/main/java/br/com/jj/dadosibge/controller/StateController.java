package br.com.jj.dadosibge.controller;

import br.com.jj.dadosibge.model.State;
import br.com.jj.dadosibge.service.StateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/state")
public class StateController {

    private final StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @GetMapping
    public ResponseEntity<List<State>> states(
            @RequestParam(required = false) Optional<String> region,
            @RequestParam(required = false) Optional<String> shortName,
            @RequestParam(required = false) Optional<Integer> id)
    {
        String paramRegion = region.orElse("");
        String paramShortName = shortName.orElse("");
        Integer paramId = id.orElse(0);

        List<State> list = null;

        if(!paramRegion.isEmpty()){
            list = stateService.findByRegion(paramRegion);
        } else if(paramId==0 && paramShortName.isEmpty()){
            list = stateService.findAll(); //paramRegion.isEmpty()? service.findAll(): service.findByRegion(paramRegion);
        }
        else{
            State state = (paramId>0)? stateService.findById(paramId): stateService.findByShortName(paramShortName);
            if(state!=null)
                list = Arrays.asList(state);
        }

        if(list == null)
            return ResponseEntity.badRequest().build();
        else if (list.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(list);
    }
}
