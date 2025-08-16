package com.curie.curieapp.controller;


import com.curie.curieapp.dto.request.MembroForumRequest;
import com.curie.curieapp.dto.response.MembroForumResponse;
import com.curie.curieapp.service.MembroForumService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/membroForum")
@AllArgsConstructor
public class MembroForumController {
    private final MembroForumService membroForumService;

    @PostMapping("/save")
    public ResponseEntity<MembroForumResponse> save(@RequestBody MembroForumRequest dto) {
        MembroForumResponse response = membroForumService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    public ResponseEntity<List<MembroForumResponse>> getAll() {
        List<MembroForumResponse> membrosForum = membroForumService.getAll();
        return ResponseEntity.ok(membrosForum);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MembroForumResponse> getById(Long id) {
        return ResponseEntity.ok(membroForumService.getById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MembroForumResponse> update(Long id, @RequestBody MembroForumRequest dto) {
        MembroForumResponse response = membroForumService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(Long id) {
        membroForumService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
