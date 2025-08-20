package com.curie.curieapp.controller;


import com.curie.curieapp.dto.request.ComportamentoRequest;
import com.curie.curieapp.dto.response.ComportamentoResponse;
import com.curie.curieapp.service.ComportamentoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comportamento")
@AllArgsConstructor
public class ComportamentoController {
    private final ComportamentoService comportamentoService;

    @PostMapping("/save")
    public ResponseEntity<ComportamentoResponse> save(@RequestBody ComportamentoRequest dto) {
        ComportamentoResponse response = comportamentoService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    public ResponseEntity<List<ComportamentoResponse>> getAll() {
        List<ComportamentoResponse> comportamento = comportamentoService.getAll();
        return ResponseEntity.ok(comportamento);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComportamentoResponse> getById(Long id) {
        return ResponseEntity.ok(comportamentoService.getById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ComportamentoResponse> update(Long id, @RequestBody ComportamentoRequest dto) {
        ComportamentoResponse response = comportamentoService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(Long id) {
        comportamentoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
