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

import java.util.ArrayList;
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

    public MetaResponse getById(Long id) {
        Meta meta = metaRepository.findById(id).orElseThrow(() -> new RuntimeException("Meta não encontrada"));
        return metaMapper.toResponseDTO(meta);
    }

    // listar tarefas de um usuário
    public List<MetaResponse> getByUsuario(Long userId) {
        return metaRepository.getByUsuario(userId)
                .stream()
                .map(metaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // listar tarefas por nome e usuário
    public List<MetaResponse> getByNome(String nome, Long userId) {
        return metaRepository.getByNome(nome, userId)
                .stream()
                .map(metaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // listar tarefas por prazo e usuário
    public List<MetaResponse> getByPrazo(Long userId) {
        return metaRepository.getByPrazo(userId)
                .stream()
                .map(metaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // listar tarefas por prioridade e usuário
    public List<MetaResponse> getByPrioridade(Long userId) {
        List<MetaResponse> result = new ArrayList<>();

        List<Meta> baixa = metaRepository.getByPrioridade("baixa", userId);
        List<Meta> media = metaRepository.getByPrioridade("media", userId);
        List<Meta> alta = metaRepository.getByPrioridade("alta", userId);

        alta.forEach(meta -> result.add(metaMapper.toResponseDTO(meta)));
        media.forEach(meta -> result.add(metaMapper.toResponseDTO(meta)));
        baixa.forEach(meta -> result.add(metaMapper.toResponseDTO(meta)));

        return result;
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
