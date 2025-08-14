package com.curie.curieapp.controller;


import com.curie.curieapp.dto.request.CategoriaRequest;
import com.curie.curieapp.dto.response.CategoriaResponse;
import com.curie.curieapp.service.CategoriaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@AllArgsConstructor
public class CategoriaController {
    private final CategoriaService categoriaService;

    @PostMapping("/save")
    public ResponseEntity<CategoriaResponse> save(@RequestBody CategoriaRequest dto) {
        CategoriaResponse response = categoriaService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    public ResponseEntity<List<CategoriaResponse>> getAll() {
        List<CategoriaResponse> categoria = categoriaService.getAll();
        return ResponseEntity.ok(categoria);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> getById(Long id) {
        return ResponseEntity.ok(categoriaService.getById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CategoriaResponse> update(Long id, @RequestBody CategoriaRequest dto) {
        CategoriaResponse response = categoriaService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(Long id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
