package com.curie.curieapp.service;


import com.curie.curieapp.dto.request.ForumRequest;
import com.curie.curieapp.dto.response.ForumResponse;
import com.curie.curieapp.entities.Forum;
import com.curie.curieapp.mapper.ForumMapper;
import com.curie.curieapp.repository.ForumRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class ForumService {

    @Autowired
    private final ForumRepository forumRepository;

    @Autowired
    private final ForumMapper forumMapper;

    public ForumResponse save(ForumRequest dto) {
        Forum forum = forumMapper.toEntity(dto);
        return forumMapper.toResponseDTO(forumRepository.save(forum));
    }

    public List<ForumResponse> getAll() {
        return forumRepository.findAll()
                .stream()
                .map(forumMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ForumResponse getById(Long id) {
        Forum forum = forumRepository.findById(id).orElseThrow(() -> new RuntimeException("Forum não encontrado"));
        return forumMapper.toResponseDTO(forum);
    }

    public ForumResponse update(Long id, ForumRequest dto) {
        Forum forum = forumRepository.findById(id).orElseThrow(() -> new RuntimeException("Forum não encontrado"));
        forum.setNome(dto.nome());
        forum.setCriacao(dto.criacao());
        return forumMapper.toResponseDTO(forumRepository.save(forum));
    }

    public void delete(Long id) {
        forumRepository.deleteById(id);
    }

}
