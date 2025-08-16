package com.curie.curieapp.service;



import com.curie.curieapp.dto.request.MembroForumRequest;
import com.curie.curieapp.dto.response.MembroForumResponse;
import com.curie.curieapp.entities.MembroForum;
import com.curie.curieapp.mapper.MembroForumMapper;
import com.curie.curieapp.repository.MembroForumReposity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MembroForumService {
    @Autowired
    private final MembroForumReposity membroForumReposity;

    @Autowired
    private final MembroForumMapper membroForumMapper;

    public MembroForumResponse save(MembroForumRequest dto) {
        MembroForum membroForum = membroForumMapper.toEntity(dto);
        return membroForumMapper.toResponseDTO(membroForumReposity.save(membroForum));
    }

    public List<MembroForumResponse> getAll() {
        return membroForumReposity.findAll()
                .stream()
                .map(membroForumMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public MembroForumResponse getById(Long id) {
        MembroForum membroForum = membroForumReposity.findById(id).orElseThrow(() -> new RuntimeException("Membro do Forum não encontrado"));
        return membroForumMapper.toResponseDTO(membroForum);
    }

    public MembroForumResponse update(Long id, MembroForumRequest dto) {
        MembroForum membroForum = membroForumReposity.findById(id).orElseThrow(() -> new RuntimeException("Membro do Forum não encontrado"));
        membroForum.setEntrada(dto.entrada());
        membroForum.setUltimaAparicao(dto.ultimaAparicao());
        return membroForumMapper.toResponseDTO(membroForumReposity.save(membroForum));
    }

    public void delete(Long id) {
        membroForumReposity.deleteById(id);
    }
}
