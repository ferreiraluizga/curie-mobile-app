package com.curie.curieapp.controller;


import com.curie.curieapp.dto.request.TemperamentoRequest;
import com.curie.curieapp.dto.response.TemperamentoResponse;
import com.curie.curieapp.service.TemperamentoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/temperamento")
@AllArgsConstructor
public class TemperamentoController {
    private final TemperamentoService temperamentoService;

    @PostMapping("/save")
    public ResponseEntity<TemperamentoResponse> save(@RequestBody TemperamentoRequest dto) {
        TemperamentoResponse response = temperamentoService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    public ResponseEntity<List<TemperamentoResponse>> getAll() {
        List<TemperamentoResponse> temperamento = temperamentoService.getAll();
        return ResponseEntity.ok(temperamento);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TemperamentoResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(temperamentoService.getById(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        temperamentoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
