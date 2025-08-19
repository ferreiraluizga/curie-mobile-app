package com.curie.curieapp.service;



import com.curie.curieapp.dto.request.AreaConhecimentoRequest;
import com.curie.curieapp.dto.response.AreaConhecimentoResponse;
import com.curie.curieapp.entities.AreaConhecimento;
import com.curie.curieapp.mapper.AreaConhecimentoMapper;
import com.curie.curieapp.repository.AreaConhecimentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@AllArgsConstructor
public class AreaConhecimentoService {
    @Autowired
    private final AreaConhecimentoRepository areaConhecimentoRepository;

    @Autowired
    private final AreaConhecimentoMapper areaConhecimentoMapper;

    public AreaConhecimentoResponse save(AreaConhecimentoRequest dto) {
        AreaConhecimento areaConhecimento = areaConhecimentoMapper.toEntity(dto);
        return areaConhecimentoMapper.toResponseDTO(areaConhecimentoRepository.save(areaConhecimento));
    }

    public List<AreaConhecimentoResponse> getAll() {
        return areaConhecimentoRepository.findAll()
                .stream()
                .map(areaConhecimentoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public AreaConhecimentoResponse getById(Long id) {
        AreaConhecimento areaConhecimento = areaConhecimentoRepository.findById(id).orElseThrow(() -> new RuntimeException("Área de conhecimento não encontrado"));
        return areaConhecimentoMapper.toResponseDTO(areaConhecimento);
    }

    public AreaConhecimentoResponse update(Long id, AreaConhecimentoRequest dto) {
        AreaConhecimento areaConhecimento = areaConhecimentoRepository.findById(id).orElseThrow(() -> new RuntimeException("Área de conhecimento não encontrado"));
        areaConhecimento.setNome(dto.nome());
        areaConhecimento.setMaterias(dto.materias());
        return areaConhecimentoMapper.toResponseDTO(areaConhecimentoRepository.save(areaConhecimento));
    }

    public void delete(Long id) {
        areaConhecimentoRepository.deleteById(id);
    }
}
