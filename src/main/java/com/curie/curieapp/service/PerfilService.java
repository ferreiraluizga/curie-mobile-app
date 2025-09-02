package com.curie.curieapp.service;


import com.curie.curieapp.dto.request.PerfilRequest;
import com.curie.curieapp.dto.response.PerfilResponse;
import com.curie.curieapp.entities.Perfil;
import com.curie.curieapp.mapper.PerfilMapper;
import com.curie.curieapp.repository.PerfilRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PerfilService {
    @Autowired
    private final PerfilRepository perfilRepository;

    @Autowired
    private final PerfilMapper perfilMapper;

    public PerfilResponse save(PerfilRequest dto) {

        Perfil perfil = perfilMapper.toEntity(dto);
        return perfilMapper.toResponseDTO(perfilRepository.save(perfil));
    }

    public List<PerfilResponse> getAll() {
        return perfilRepository.findAll()
                .stream()
                .map(perfilMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public PerfilResponse getById(Long id) {
        Perfil perfil = perfilRepository.findById(id).orElseThrow(() -> new RuntimeException("Perfil não encontrado"));
        return perfilMapper.toResponseDTO(perfil);
    }

    public PerfilResponse update(Long id, PerfilRequest dto) {
        Perfil perfil = perfilRepository.findById(id).orElseThrow(() -> new RuntimeException("Perfil não encontrado"));
        perfil.setDescricao(dto.descricao());
        perfil.setComportamento(dto.comportamento());
        perfil.setTemperamento(dto.temperamento());
        perfil.setForca(dto.forca());
        perfil.setFraqueza(dto.fraqueza());
        return perfilMapper.toResponseDTO(perfilRepository.save(perfil));
    }

    public void delete(Long id) {
        perfilRepository.deleteById(id);
    }
}