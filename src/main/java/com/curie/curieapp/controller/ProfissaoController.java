package com.curie.curieapp.controller;

import com.curie.curieapp.dto.request.ProfissaoRequest;
import com.curie.curieapp.dto.response.ProfissaoResponse;
import com.curie.curieapp.service.ProfissaoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profissao")
@AllArgsConstructor
public class ProfissaoController {

    private final ProfissaoService profissaoService;

    @GetMapping()
    public ResponseEntity<List<ProfissaoResponse>> getAll() {
        List<ProfissaoResponse> areaConhecimento = profissaoService.getAll();
        return ResponseEntity.ok(areaConhecimento);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfissaoResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(profissaoService.getById(id));
    }

}
