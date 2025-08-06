package com.curie.curieapp.controller;

import com.curie.curieapp.dto.request.AssinaturaRequest;
import com.curie.curieapp.dto.response.AssinaturaResponse;
import com.curie.curieapp.service.AssinaturaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assinatura")
@AllArgsConstructor
public class AssinaturaController {
    
    private final AssinaturaService assinaturaService;
    @PostMapping("/save")
    public ResponseEntity<AssinaturaResponse> save(@RequestBody AssinaturaRequest dto) {
        AssinaturaResponse response = assinaturaService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    public ResponseEntity<List<AssinaturaResponse>> getAll() {
        List<AssinaturaResponse> assinatura = assinaturaService.getAll();
        return ResponseEntity.ok(assinatura);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssinaturaResponse> getById(Long id) {return ResponseEntity.ok(assinaturaService.getById(id));}

    @PutMapping("/update/{id}")
    public ResponseEntity<AssinaturaResponse> update(Long id, @RequestBody AssinaturaRequest dto) {
        AssinaturaResponse response = assinaturaService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> assinatura(Long id) {
        assinaturaService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}
