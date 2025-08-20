package com.curie.curieapp.service;

import com.curie.curieapp.dto.request.AreaCarreiraRequest;
import com.curie.curieapp.dto.request.TarefaRequest;
import com.curie.curieapp.dto.response.AreaCarreiraResponse;
import com.curie.curieapp.entities.AreaCarreira;
import com.curie.curieapp.mapper.AreaCarreiraMapper;
import com.curie.curieapp.repository.AreaCarreiraRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AreaCarreiraService {

    @Autowired
    private final AreaCarreiraRepository areaCarreiraRepository;

    @Autowired
    private final AreaCarreiraMapper areaCarreiraMapper;

    public AreaCarreiraResponse save(AreaCarreiraRequest dto) {
        AreaCarreira areaCarreira = areaCarreiraMapper.toEntity(dto);
        return areaCarreiraMapper.toResponseDTO(areaCarreiraRepository.save(areaCarreira));
    }

    public List<AreaCarreiraResponse> getAll() {
        return areaCarreiraRepository.findAll()
                .stream()
                .map(areaCarreiraMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public AreaCarreiraResponse getById(Long id) {
        AreaCarreira areaCarreira = areaCarreiraRepository.findById(id).orElseThrow(() -> new RuntimeException("Area de carreira não encontrada"));
        return areaCarreiraMapper.toResponseDTO(areaCarreira);
    }

    public AreaCarreiraResponse update(Long id, AreaCarreiraRequest dto) {
        AreaCarreira areaCarreira = areaCarreiraRepository.findById(id).orElseThrow(() -> new RuntimeException("Area de carreira não encontrada"));
        areaCarreira.setNome(dto.nome());
        areaCarreira.setDescricao(dto.descricao());
        return areaCarreiraMapper.toResponseDTO(areaCarreiraRepository.save(areaCarreira));
    }

    public void delete(Long id) {
        areaCarreiraRepository.deleteById(id);
    }
}
