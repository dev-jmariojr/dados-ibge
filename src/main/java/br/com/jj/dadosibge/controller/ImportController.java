package br.com.jj.dadosibge.controller;

import br.com.jj.dadosibge.model.ImportIBGE;
import br.com.jj.dadosibge.service.ImportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("v1/import")
public class ImportController {

    private static String MSG_IMPORT_NEW = "Nova importação iniciada";
    private static String MSG_IMPORT_PROGRESS = "Importação em andamento";
    private static String MSG_IMPORT_ABORT = "Importação abortada";

    private final ImportService service;

    public ImportController(final ImportService service) {
        this.service = service;
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping(){
        return ResponseEntity.ok(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss")));
    }

    @GetMapping
    public ResponseEntity<List<ImportIBGE>> allImports(){
        return ResponseEntity.ok(service.allImport());
    }

    @PutMapping("/{id}/abort")
    public ResponseEntity<String> abortImports(@PathVariable String id){
        Boolean abort = service.abortImport(id);
        if(abort)
            return ResponseEntity.ok(MSG_IMPORT_ABORT);
        else
            return ResponseEntity.noContent().build();
    }

    @PostMapping("/new")
    public ResponseEntity<String> newImportIBGE(){
        Boolean importIBGE = service.newImport();
        if(importIBGE)
            return ResponseEntity.ok(MSG_IMPORT_NEW);
        else
            return ResponseEntity.ok(MSG_IMPORT_PROGRESS);
    }

}
