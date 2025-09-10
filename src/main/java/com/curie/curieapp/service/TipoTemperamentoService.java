package com.curie.curieapp.service;


import com.curie.curieapp.dto.request.TipoTemperamentoRequest;
import com.curie.curieapp.dto.response.TipoTemperamentoResponse;
import com.curie.curieapp.entities.TipoTemperamento;
import com.curie.curieapp.mapper.TipoTemperamentoMapper;
import com.curie.curieapp.repository.TipoTemperamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TipoTemperamentoService {
    @Autowired
    private final TipoTemperamentoRepository tipoTemperamentoRepository;

    @Autowired
    private final TipoTemperamentoMapper tipoTemperamentoMapper;

    public List<TipoTemperamentoResponse> getAll() {
        return tipoTemperamentoRepository.findAll()
                .stream()
                .map(tipoTemperamentoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public TipoTemperamentoResponse getById(Long id) {
        TipoTemperamento tipoTemperamento = tipoTemperamentoRepository.findById(id).orElseThrow(() -> new RuntimeException("Tipo de temperamento não encontrado"));
        return tipoTemperamentoMapper.toResponseDTO(tipoTemperamento);
    }
}
