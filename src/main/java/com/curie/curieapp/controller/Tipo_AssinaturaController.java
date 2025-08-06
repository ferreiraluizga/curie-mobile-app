package com.curie.curieapp.controller;


import com.curie.curieapp.dto.request.Tipo_AssinaturaRequest;
import com.curie.curieapp.dto.response.Tipo_AssinaturaResponse;
import com.curie.curieapp.service.Tipo_AssinaturaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipo_assinatura")
@AllArgsConstructor
public class Tipo_AssinaturaController {
    private final Tipo_AssinaturaService tipo_assinaturaService;

    @PostMapping("/save")
    public ResponseEntity<Tipo_AssinaturaResponse> save(@RequestBody Tipo_AssinaturaRequest dto) {
        Tipo_AssinaturaResponse response = tipo_assinaturaService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    public ResponseEntity<List<Tipo_AssinaturaResponse>> getAll() {
        List<Tipo_AssinaturaResponse> tipo_assinaturas = tipo_assinaturaService.getAll();
        return ResponseEntity.ok(tipo_assinaturas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tipo_AssinaturaResponse> getById(Long id) { return ResponseEntity.ok(tipo_assinaturaService.getById(id));}

    @PutMapping("/update/{id}")
    public ResponseEntity<Tipo_AssinaturaResponse> update(Long id, @RequestBody Tipo_AssinaturaRequest dto) {
        Tipo_AssinaturaResponse response = tipo_assinaturaService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(Long id) {
        tipo_assinaturaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
