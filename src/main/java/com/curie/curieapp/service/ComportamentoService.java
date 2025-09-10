package com.curie.curieapp.service;


import com.curie.curieapp.dto.request.ComportamentoRequest;
import com.curie.curieapp.dto.response.ComportamentoResponse;
import com.curie.curieapp.entities.Comportamento;
import com.curie.curieapp.mapper.ComportamentoMapper;
import com.curie.curieapp.repository.ComportamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ComportamentoService {
    @Autowired
    private final ComportamentoRepository comportamentoRepository;

    @Autowired
    private final ComportamentoMapper comportamentoMapper;

    public ComportamentoResponse save(ComportamentoRequest dto) {
        Comportamento comportamento = comportamentoMapper.toEntity(dto);
        return comportamentoMapper.toResponseDTO(comportamentoRepository.save(comportamento));
    }

    public List<ComportamentoResponse> getAll() {
        return comportamentoRepository.findAll()
                .stream()
                .map(comportamentoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ComportamentoResponse getById(Long id) {
        Comportamento comportamento = comportamentoRepository.findById(id).orElseThrow(() -> new RuntimeException("Mensagem não encontrada"));
        return comportamentoMapper.toResponseDTO(comportamento);
    }

    public void delete(Long id) {
        comportamentoRepository.deleteById(id);
    }

    public void deleteByUsuario(Long id) {
        comportamentoRepository.deleteByUsuario(id);
    }
}
