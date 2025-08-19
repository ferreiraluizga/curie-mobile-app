package com.curie.curieapp.service;


import com.curie.curieapp.dto.request.TipoComportamentoRequest;
import com.curie.curieapp.dto.response.TipoComportamentoResponse;
import com.curie.curieapp.entities.TipoComportamento;
import com.curie.curieapp.mapper.TipoComportamentoMapper;
import com.curie.curieapp.repository.TipoComportamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@AllArgsConstructor
public class TipoComportamentoService {
    @Autowired
    private final TipoComportamentoRepository tipoComportamentoRepository;

    @Autowired
    private final TipoComportamentoMapper tipoComportamentoMapper;

    public TipoComportamentoResponse save(TipoComportamentoRequest dto) {
        TipoComportamento tipoComportamento = tipoComportamentoMapper.toEntity(dto);
        return tipoComportamentoMapper.toResponseDTO(tipoComportamentoRepository.save(tipoComportamento));
    }

    public List<TipoComportamentoResponse> getAll() {
        return tipoComportamentoRepository.findAll()
                .stream()
                .map(tipoComportamentoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public TipoComportamentoResponse getById(Long id) {
        TipoComportamento tipoComportamento = tipoComportamentoRepository.findById(id).orElseThrow(() -> new RuntimeException("Tipo de comportamento não encontrado"));
        return tipoComportamentoMapper.toResponseDTO(tipoComportamento);
    }

    public TipoComportamentoResponse update(Long id, TipoComportamentoRequest dto) {
        TipoComportamento tipoComportamento = tipoComportamentoRepository.findById(id).orElseThrow(() -> new RuntimeException("Tipo de comportamento não encontrado"));
        tipoComportamento.setNome(dto.nome());
        tipoComportamento.setDescricao(dto.descricao());
        return tipoComportamentoMapper.toResponseDTO(tipoComportamentoRepository.save(tipoComportamento));
    }

    public void delete(Long id) {
        tipoComportamentoRepository.deleteById(id);
    }
}
