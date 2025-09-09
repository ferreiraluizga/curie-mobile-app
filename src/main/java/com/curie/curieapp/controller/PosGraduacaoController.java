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

    @GetMapping()
    public ResponseEntity<List<PosGraduacaoResponse>> getAll() {
        List<PosGraduacaoResponse> areaConhecimento = posGraduacaoService.getAll();
        return ResponseEntity.ok(areaConhecimento);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PosGraduacaoResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(posGraduacaoService.getById(id));
    }

}
