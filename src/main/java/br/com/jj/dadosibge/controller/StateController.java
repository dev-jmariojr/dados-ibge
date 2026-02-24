package br.com.jj.dadosibge.controller;

import br.com.jj.dadosibge.dto.StateParams;
import br.com.jj.dadosibge.model.State;
import br.com.jj.dadosibge.service.StateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/state")
public class StateController {

    private final StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @GetMapping
    public ResponseEntity<List<State>> states(
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String shortName,
            @RequestParam(required = false) Integer id)
    {
        var params = new StateParams(shortName, region, id);
        List<State> states = stateService.find(params);

        if(states.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(states);
    }
}
