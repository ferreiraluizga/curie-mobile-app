package com.curie.curieapp.controller;

import com.curie.curieapp.dto.request.TarefaRequest;
import com.curie.curieapp.dto.response.TarefaResponse;
import com.curie.curieapp.service.TarefaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
@AllArgsConstructor
public class TarefaController {

    private final TarefaService tarefaService;

    @PostMapping("/save")
    public ResponseEntity<TarefaResponse> save(@RequestBody TarefaRequest dto) {
        TarefaResponse response = tarefaService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    public ResponseEntity<List<TarefaResponse>> getAll() {
        List<TarefaResponse> tarefas = tarefaService.getAll();
        return ResponseEntity.ok(tarefas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarefaResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(tarefaService.getById(id));
    }

    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<TarefaResponse>> getByUsuario(@PathVariable Long userId) {
        return ResponseEntity.ok(tarefaService.getByUsuario(userId));
    }

    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<TarefaResponse>> getByNome(@PathVariable Long userId, @RequestParam String nome) {
        return ResponseEntity.ok(tarefaService.getByNome(nome, userId));
    }

    @GetMapping("/usuario/{userId}/ordenar-por-prazo")
    public ResponseEntity<List<TarefaResponse>> getByPrazo(@PathVariable Long userId) {
        return ResponseEntity.ok(tarefaService.getByPrazo(userId));
    }

    @GetMapping("/usuario/{userId}/ordenar-por-prioridade")
    public ResponseEntity<List<TarefaResponse>> getByPrioridade(@PathVariable Long userId) {
        return ResponseEntity.ok(tarefaService.getByPrioridade(userId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TarefaResponse> update(@PathVariable Long id, @RequestBody TarefaRequest dto) {
        TarefaResponse response = tarefaService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tarefaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
