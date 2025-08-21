package com.curie.curieapp.controller;

import com.curie.curieapp.dto.request.AreaCarreiraRequest;
import com.curie.curieapp.dto.response.AreaCarreiraResponse;
import com.curie.curieapp.service.AreaCarreiraService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/areaCarreira")
@AllArgsConstructor
public class AreaCarreiraController {

    private final AreaCarreiraService areaCarreiraService;

    @PostMapping("/save")
    public ResponseEntity<AreaCarreiraResponse> save(@RequestBody AreaCarreiraRequest dto) {
        AreaCarreiraResponse response = areaCarreiraService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    public ResponseEntity<List<AreaCarreiraResponse>> getAll() {
        List<AreaCarreiraResponse> areaConhecimento = areaCarreiraService.getAll();
        return ResponseEntity.ok(areaConhecimento);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AreaCarreiraResponse> getById(Long id) {
        return ResponseEntity.ok(areaCarreiraService.getById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AreaCarreiraResponse> update(Long id, @RequestBody AreaCarreiraRequest dto) {
        AreaCarreiraResponse response = areaCarreiraService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(Long id) {
        areaCarreiraService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
