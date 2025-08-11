package com.curie.curieapp.service;

import com.curie.curieapp.dto.request.MetaRequest;
import com.curie.curieapp.dto.response.MetaResponse;
import com.curie.curieapp.dto.response.TarefaResponse;
import com.curie.curieapp.entities.Meta;
import com.curie.curieapp.entities.Tarefa;
import com.curie.curieapp.entities.enums.Prioridade;
import com.curie.curieapp.entities.enums.Status;
import com.curie.curieapp.mapper.MetaMapper;
import com.curie.curieapp.repository.MetaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MetaService {

    @Autowired
    private final MetaRepository metaRepository;

    @Autowired
    private final MetaMapper metaMapper;

    public MetaResponse save(MetaRequest dto) {
        Meta meta = metaMapper.toEntity(dto);
        return metaMapper.toResponseDTO(metaRepository.save(meta));
    }

    public List<MetaResponse> getAll() {
        return metaRepository.findAll()
                .stream()
                .map(metaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public MetaResponse getById(Long id) {
        Meta meta = metaRepository.findById(id).orElseThrow(() -> new RuntimeException("Meta não encontrada"));
        return metaMapper.toResponseDTO(meta);
    }

    public MetaResponse update(Long id, MetaRequest dto) {
        Meta meta = metaRepository.findById(id).orElseThrow(() -> new RuntimeException("Meta não encontrada"));
        meta.setObjetivo(dto.objetivo());
        meta.setDescricao(dto.descricao());
        meta.setInicio(dto.inicio());
        meta.setFim(dto.fim());
        meta.setPrioridade(Prioridade.valueOf(dto.prioridade().toLowerCase()));
        meta.setStatus(Status.valueOf(dto.status().toLowerCase()));
        return metaMapper.toResponseDTO(metaRepository.save(meta));
    }

    public void delete(Long id) {
        metaRepository.deleteById(id);
    }

}
