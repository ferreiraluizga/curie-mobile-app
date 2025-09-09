package com.curie.curieapp.controller;

import com.curie.curieapp.dto.request.GraduacaoRequest;
import com.curie.curieapp.dto.response.GraduacaoResponse;
import com.curie.curieapp.service.GraduacaoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/graduacao")
@AllArgsConstructor
public class GraduacaoController {

    private final GraduacaoService graduacaoService;

    @GetMapping()
    public ResponseEntity<List<GraduacaoResponse>> getAll() {
        List<GraduacaoResponse> responses = graduacaoService.getAll();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GraduacaoResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(graduacaoService.getById(id));
    }

}
