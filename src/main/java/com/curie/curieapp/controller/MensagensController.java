package com.curie.curieapp.controller;


import com.curie.curieapp.dto.request.MensagensRequest;
import com.curie.curieapp.dto.response.MensagensResponse;
import com.curie.curieapp.service.MensagensService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mensagens")
@AllArgsConstructor
public class MensagensController {
    private final MensagensService mensagensService;

    @PostMapping("/save")
    public ResponseEntity<MensagensResponse> save(@RequestBody MensagensRequest dto) {
        MensagensResponse response = mensagensService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    public ResponseEntity<List<MensagensResponse>> getAll() {
        List<MensagensResponse> mensagensForum = mensagensService.getAll();
        return ResponseEntity.ok(mensagensForum);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MensagensResponse> getById(Long id) {
        return ResponseEntity.ok(mensagensService.getById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MensagensResponse> update(Long id, @RequestBody MensagensRequest dto) {
        MensagensResponse response = mensagensService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(Long id) {
        mensagensService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
