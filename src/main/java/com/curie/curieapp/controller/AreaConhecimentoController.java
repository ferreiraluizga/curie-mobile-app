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

    @PostMapping("/save")
    public ResponseEntity<AreaConhecimentoResponse> save(@RequestBody AreaConhecimentoRequest dto) {
        AreaConhecimentoResponse response = areaConhecimentoService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    public ResponseEntity<List<AreaConhecimentoResponse>> getAll() {
        List<AreaConhecimentoResponse> areaConhecimento = areaConhecimentoService.getAll();
        return ResponseEntity.ok(areaConhecimento);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AreaConhecimentoResponse> getById(Long id) {
        return ResponseEntity.ok(areaConhecimentoService.getById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AreaConhecimentoResponse> update(Long id, @RequestBody AreaConhecimentoRequest dto) {
        AreaConhecimentoResponse response = areaConhecimentoService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(Long id) {
        areaConhecimentoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}