package com.curie.curieapp.controller;

import com.curie.curieapp.dto.request.MetaRequest;
import com.curie.curieapp.dto.request.TarefaRequest;
import com.curie.curieapp.dto.response.MetaResponse;
import com.curie.curieapp.dto.response.TarefaResponse;
import com.curie.curieapp.service.MetaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/metas")
@AllArgsConstructor
public class MetaController {

    private final MetaService metaService;

    @PostMapping("/save")
    public ResponseEntity<MetaResponse> save(@RequestBody MetaRequest dto) {
        MetaResponse response = metaService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    public ResponseEntity<List<MetaResponse>> getAll() {
        List<MetaResponse> tarefas = metaService.getAll();
        return ResponseEntity.ok(tarefas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetaResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(metaService.getById(id));
    }

    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<MetaResponse>> getByUsuario(@PathVariable Long userId) {
        return ResponseEntity.ok(metaService.getByUsuario(userId));
    }

    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<MetaResponse>> getByNome(@PathVariable Long userId, @RequestParam String nome) {
        return ResponseEntity.ok(metaService.getByNome(nome, userId));
    }

    @GetMapping("/usuario/{userId}/ordenar-por-prazo")
    public ResponseEntity<List<MetaResponse>> getByPrazo(@PathVariable Long userId) {
        return ResponseEntity.ok(metaService.getByPrazo(userId));
    }

    @GetMapping("/usuario/{userId}/ordenar-por-prioridade")
    public ResponseEntity<List<MetaResponse>> getByPrioridade(@PathVariable Long userId) {
        return ResponseEntity.ok(metaService.getByPrioridade(userId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MetaResponse> update(@PathVariable Long id, @RequestBody MetaRequest dto) {
        MetaResponse response = metaService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        metaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
