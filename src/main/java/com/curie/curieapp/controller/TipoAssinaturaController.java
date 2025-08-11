package com.curie.curieapp.controller;


import com.curie.curieapp.dto.request.TipoAssinaturaRequest;
import com.curie.curieapp.dto.response.TipoAssinaturaResponse;
import com.curie.curieapp.service.TipoAssinaturaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipoAssinatura")
@AllArgsConstructor
public class TipoAssinaturaController {
    private final TipoAssinaturaService tipoAssinaturaService;

    @PostMapping("/save")
    public ResponseEntity<TipoAssinaturaResponse> save(@RequestBody TipoAssinaturaRequest dto) {
        TipoAssinaturaResponse response = tipoAssinaturaService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    public ResponseEntity<List<TipoAssinaturaResponse>> getAll() {
        List<TipoAssinaturaResponse> tiposAssinatura = tipoAssinaturaService.getAll();
        return ResponseEntity.ok(tiposAssinatura);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoAssinaturaResponse> getById(Long id) { return ResponseEntity.ok(tipoAssinaturaService.getById(id));}

    @PutMapping("/update/{id}")
    public ResponseEntity<TipoAssinaturaResponse> update(Long id, @RequestBody TipoAssinaturaRequest dto) {
        TipoAssinaturaResponse response = tipoAssinaturaService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(Long id) {
        tipoAssinaturaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
