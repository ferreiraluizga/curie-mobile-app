package com.curie.curieapp.controller;

import com.curie.curieapp.dto.request.EquipeRequest;
import com.curie.curieapp.dto.response.EquipeResponse;
import com.curie.curieapp.service.EquipeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipe")
@AllArgsConstructor
public class EquipeController {

    private final EquipeService equipeService;

    @PostMapping("/save")
    public ResponseEntity<EquipeResponse> save(@RequestBody EquipeRequest dto) {
        EquipeResponse response = equipeService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    public ResponseEntity<List<EquipeResponse>> getAll() {
        List<EquipeResponse> equipes = equipeService.getAll();
        return ResponseEntity.ok(equipes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipeResponse> getById(@PathVariable Long id) {return ResponseEntity.ok(equipeService.getById(id));}

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        equipeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
