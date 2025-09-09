package com.curie.curieapp.controller;

import com.curie.curieapp.dto.request.CarreiraRequest;
import com.curie.curieapp.dto.response.CarreiraResponse;
import com.curie.curieapp.service.CarreiraService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carreira")
@AllArgsConstructor
public class CarreiraController {

    private final CarreiraService carreiraService;

    @PostMapping("/save")
    public ResponseEntity<CarreiraResponse> save(@RequestBody CarreiraRequest dto) {
        CarreiraResponse response = carreiraService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<CarreiraResponse>> getByUsuario(@PathVariable Long id) {
        List<CarreiraResponse> carreiras = carreiraService.getByUsuario(id);
        return ResponseEntity.ok(carreiras);
    }

    @GetMapping("/usuario/{id}/mais-recente")
    public ResponseEntity<CarreiraResponse> getMaisRecente(@PathVariable Long id) {
        return ResponseEntity.ok(carreiraService.getMaisRecente(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarreiraResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(carreiraService.getById(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        carreiraService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
