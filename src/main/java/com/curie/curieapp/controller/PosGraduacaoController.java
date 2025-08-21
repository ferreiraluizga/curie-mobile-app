package com.curie.curieapp.controller;

import com.curie.curieapp.dto.request.PosGraduacaoRequest;
import com.curie.curieapp.dto.response.PosGraduacaoResponse;
import com.curie.curieapp.service.PosGraduacaoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posGraduacao")
@AllArgsConstructor
public class PosGraduacaoController {

    private final PosGraduacaoService posGraduacaoService;

    @PostMapping("/save")
    public ResponseEntity<PosGraduacaoResponse> save(@RequestBody PosGraduacaoRequest dto) {
        PosGraduacaoResponse response = posGraduacaoService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    public ResponseEntity<List<PosGraduacaoResponse>> getAll() {
        List<PosGraduacaoResponse> areaConhecimento = posGraduacaoService.getAll();
        return ResponseEntity.ok(areaConhecimento);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PosGraduacaoResponse> getById(Long id) {
        return ResponseEntity.ok(posGraduacaoService.getById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PosGraduacaoResponse> update(Long id, @RequestBody PosGraduacaoRequest dto) {
        PosGraduacaoResponse response = posGraduacaoService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(Long id) {
        posGraduacaoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
