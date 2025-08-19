package com.curie.curieapp.controller;


import com.curie.curieapp.dto.request.TipoTemperamentoRequest;
import com.curie.curieapp.dto.response.TipoTemperamentoResponse;
import com.curie.curieapp.service.TipoTemperamentoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipoTemperamento")
@AllArgsConstructor
public class TipoTemperamentoController {
    private final TipoTemperamentoService tipoTemperamentoService;

    @PostMapping("/save")
    public ResponseEntity<TipoTemperamentoResponse> save(@RequestBody TipoTemperamentoRequest dto) {
        TipoTemperamentoResponse response = tipoTemperamentoService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    public ResponseEntity<List<TipoTemperamentoResponse>> getAll() {
        List<TipoTemperamentoResponse> tipoTemperamento = tipoTemperamentoService.getAll();
        return ResponseEntity.ok(tipoTemperamento);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoTemperamentoResponse> getById(Long id) {
        return ResponseEntity.ok(tipoTemperamentoService.getById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TipoTemperamentoResponse> update(Long id, @RequestBody TipoTemperamentoRequest dto) {
        TipoTemperamentoResponse response = tipoTemperamentoService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(Long id) {
        tipoTemperamentoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
