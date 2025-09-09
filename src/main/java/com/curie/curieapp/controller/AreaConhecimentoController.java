package com.curie.curieapp.controller;


import com.curie.curieapp.dto.request.AreaConhecimentoRequest;
import com.curie.curieapp.dto.response.AreaConhecimentoResponse;
import com.curie.curieapp.service.AreaConhecimentoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/areaConhecimento")
@AllArgsConstructor
public class AreaConhecimentoController {
    private final AreaConhecimentoService areaConhecimentoService;

    @GetMapping()
    public ResponseEntity<List<AreaConhecimentoResponse>> getAll() {
        List<AreaConhecimentoResponse> areaConhecimento = areaConhecimentoService.getAll();
        return ResponseEntity.ok(areaConhecimento);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AreaConhecimentoResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(areaConhecimentoService.getById(id));
    }
}