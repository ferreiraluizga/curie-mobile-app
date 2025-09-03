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

    @GetMapping()
    public ResponseEntity<List<CarreiraResponse>> getAll() {
        List<CarreiraResponse> areaConhecimento = carreiraService.getAll();
        return ResponseEntity.ok(areaConhecimento);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarreiraResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(carreiraService.getById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CarreiraResponse> update(@PathVariable Long id, @RequestBody CarreiraRequest dto) {
        CarreiraResponse response = carreiraService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        carreiraService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
