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

    @GetMapping()
    public ResponseEntity<List<AreaCarreiraResponse>> getAll() {
        List<AreaCarreiraResponse> areaConhecimento = areaCarreiraService.getAll();
        return ResponseEntity.ok(areaConhecimento);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AreaCarreiraResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(areaCarreiraService.getById(id));
    }

}
