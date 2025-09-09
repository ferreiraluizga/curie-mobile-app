package com.curie.curieapp.service;


import com.curie.curieapp.dto.request.GraduacaoRequest;
import com.curie.curieapp.dto.response.GraduacaoResponse;
import com.curie.curieapp.entities.Graduacao;
import com.curie.curieapp.mapper.GraduacaoMapper;
import com.curie.curieapp.repository.GraduacaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GraduacaoService {

    @Autowired
    private final GraduacaoRepository graduacaoRepository;

    @Autowired
    private final GraduacaoMapper graduacaoMapper;

    public List<GraduacaoResponse> getAll() {
        return graduacaoRepository.findAll()
                .stream()
                .map(graduacaoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public GraduacaoResponse getById(Long id) {
        Graduacao graduacao = graduacaoRepository.findById(id).orElseThrow(() -> new RuntimeException("Area de carreira não encontrada"));
        return graduacaoMapper.toResponseDTO(graduacao);
    }

}
