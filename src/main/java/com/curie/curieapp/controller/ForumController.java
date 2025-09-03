package com.curie.curieapp.controller;


import com.curie.curieapp.dto.request.ForumRequest;
import com.curie.curieapp.dto.response.ForumResponse;
import com.curie.curieapp.service.ForumService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/forum")
@AllArgsConstructor

public class ForumController {
    private final ForumService forumService;

    @PostMapping("/save")
    public ResponseEntity<ForumResponse> save(@RequestBody ForumRequest dto) {
        ForumResponse response = forumService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    public ResponseEntity<List<ForumResponse>> getAll() {
        List<ForumResponse> forum = forumService.getAll();
        return ResponseEntity.ok(forum);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ForumResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(forumService.getById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ForumResponse> update(@PathVariable Long id, @RequestBody ForumRequest dto) {
        ForumResponse response = forumService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        forumService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
