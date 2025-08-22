package com.curie.curieapp.controller;


import com.curie.curieapp.dto.request.PerfilRequest;
import com.curie.curieapp.dto.response.PerfilResponse;
import com.curie.curieapp.service.PerfilService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/perfil")
@AllArgsConstructor
public class PerfilController {
    private final PerfilService perfilService;

    @PostMapping("/save")
    public ResponseEntity<PerfilResponse> save(@RequestBody PerfilRequest dto) {
        PerfilResponse response = perfilService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    public ResponseEntity<List<PerfilResponse>> getAll() {
        List<PerfilResponse> perfil = perfilService.getAll();
        return ResponseEntity.ok(perfil);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerfilResponse> getById(Long id) {
        return ResponseEntity.ok(perfilService.getById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PerfilResponse> update(Long id, @RequestBody PerfilRequest dto) {
        PerfilResponse response = perfilService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(Long id) {
        perfilService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
