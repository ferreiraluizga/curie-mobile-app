package com.curie.curieapp.service;


import com.curie.curieapp.dto.request.TemperamentoRequest;
import com.curie.curieapp.dto.response.TemperamentoResponse;
import com.curie.curieapp.dto.response.TipoTemperamentoResponse;
import com.curie.curieapp.entities.Temperamento;
import com.curie.curieapp.mapper.TemperamentoMapper;
import com.curie.curieapp.repository.TemperamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TemperamentoService {
    @Autowired
    private final TemperamentoRepository temperamentoRepository;

    @Autowired
    private final TemperamentoMapper temperamentoMapper;

    public TemperamentoResponse save(TemperamentoRequest dto) {
        Temperamento temperamento = temperamentoMapper.toEntity(dto);
        return temperamentoMapper.toResponseDTO(temperamentoRepository.save(temperamento));
    }

    public List<TemperamentoResponse> getAll() {
        return temperamentoRepository.findAll()
                .stream()
                .map(temperamentoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public TemperamentoResponse getById(Long id) {
        Temperamento temperamento = temperamentoRepository.findById(id).orElseThrow(() -> new RuntimeException("Temperamento não encontrado"));
        return temperamentoMapper.toResponseDTO(temperamento);
    }

    public TemperamentoResponse update(Long id, TemperamentoRequest dto) {
        Temperamento temperamento = temperamentoRepository.findById(id).orElseThrow(() -> new RuntimeException("Temperamento não encontrado"));
        temperamento.setForcaAprendizado(dto.forcaAprendizado());
        temperamento.setFraquezaAprendizado(dto.fraquezaAprendizado());
        return temperamentoMapper.toResponseDTO(temperamentoRepository.save(temperamento));
    }

    public void delete(Long id) {
        temperamentoRepository.deleteById(id);
    }
}
