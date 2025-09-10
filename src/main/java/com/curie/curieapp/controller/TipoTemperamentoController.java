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

    @GetMapping()
    public ResponseEntity<List<TipoTemperamentoResponse>> getAll() {
        List<TipoTemperamentoResponse> tipoTemperamento = tipoTemperamentoService.getAll();
        return ResponseEntity.ok(tipoTemperamento);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoTemperamentoResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(tipoTemperamentoService.getById(id));
    }
}
