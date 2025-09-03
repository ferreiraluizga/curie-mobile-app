package com.curie.curieapp.controller;


import com.curie.curieapp.dto.request.TipoComportamentoRequest;
import com.curie.curieapp.dto.response.TipoComportamentoResponse;
import com.curie.curieapp.entities.TipoComportamento;
import com.curie.curieapp.service.TipoComportamentoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipoComportamento")
@AllArgsConstructor
public class TipoComportamentoController {
    private final TipoComportamentoService tipoComportamentoService;

    @PostMapping("/save")
    public ResponseEntity<TipoComportamentoResponse> save(@RequestBody TipoComportamentoRequest dto) {
        TipoComportamentoResponse response = tipoComportamentoService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    public ResponseEntity<List<TipoComportamentoResponse>> getAll() {
        List<TipoComportamentoResponse> tipoComportamento = tipoComportamentoService.getAll();
        return ResponseEntity.ok(tipoComportamento);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoComportamentoResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(tipoComportamentoService.getById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TipoComportamentoResponse> update(@PathVariable Long id, @RequestBody TipoComportamentoRequest dto) {
        TipoComportamentoResponse response = tipoComportamentoService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tipoComportamentoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
