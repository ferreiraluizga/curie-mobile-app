package com.curie.curieapp.service;

import com.curie.curieapp.dto.request.TarefaRequest;
import com.curie.curieapp.dto.response.TarefaResponse;
import com.curie.curieapp.entities.Tarefa;
import com.curie.curieapp.mapper.TarefaMapper;
import com.curie.curieapp.repository.TarefaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TarefaService {

    @Autowired
    private final TarefaRepository tarefaRepository;

    @Autowired
    private final TarefaMapper tarefaMapper;

    public TarefaResponse save(TarefaRequest dto) {
        Tarefa tarefa = tarefaMapper.toEntity(dto);
        return tarefaMapper.toResponseDTO(tarefaRepository.save(tarefa));
    }

    public List<TarefaResponse> getAll() {
        return tarefaRepository.findAll()
                .stream()
                .map(tarefaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public TarefaResponse getById(Long id) {
        Tarefa tarefa = tarefaRepository.findById(id).orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        return tarefaMapper.toResponseDTO(tarefa);
    }

    public TarefaResponse update(Long id, TarefaRequest dto) {
        Tarefa tarefa = tarefaRepository.findById(id).orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        tarefa.setNome(dto.nome());
        tarefa.setPrazo(dto.prazo());
        tarefa.setPrioridade(Tarefa.Prioridade.valueOf(dto.prioridade().toLowerCase()));
        tarefa.setStatus(Tarefa.Status.valueOf(dto.status().toLowerCase()));
        return tarefaMapper.toResponseDTO(tarefaRepository.save(tarefa));
    }

    public void delete(Long id) {
        tarefaRepository.deleteById(id);
    }

}
