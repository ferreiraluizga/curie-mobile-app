package com.curie.curieapp.service;

import com.curie.curieapp.dto.request.TarefaRequest;
import com.curie.curieapp.dto.response.TarefaResponse;
import com.curie.curieapp.entities.Tarefa;
import com.curie.curieapp.entities.enums.Prioridade;
import com.curie.curieapp.entities.enums.Status;
import com.curie.curieapp.mapper.TarefaMapper;
import com.curie.curieapp.repository.TarefaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public TarefaResponse getById(Long id) {
        Tarefa tarefa = tarefaRepository.findById(id).orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        return tarefaMapper.toResponseDTO(tarefa);
    }

    // listar tarefas de um usuário
    public List<TarefaResponse> getByUsuario(Long userId) {
        return tarefaRepository.getByUsuario(userId)
                .stream()
                .map(tarefaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // listar tarefas por nome e usuário
    public List<TarefaResponse> getByNome(String nome, Long userId) {
        return tarefaRepository.getByNome(nome, userId)
                .stream()
                .map(tarefaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // listar tarefas por prazo e usuário
    public List<TarefaResponse> getByPrazo(Long userId) {
        return tarefaRepository.getByPrazo(userId)
                .stream()
                .map(tarefaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // listar tarefas por prioridade e usuário
    public List<TarefaResponse> getByPrioridade(Long userId) {
        List<TarefaResponse> result = new ArrayList<>();

        List<Tarefa> baixa = tarefaRepository.getByPrioridade("baixa", userId);
        List<Tarefa> media = tarefaRepository.getByPrioridade("media", userId);
        List<Tarefa> alta = tarefaRepository.getByPrioridade("alta", userId);

        alta.forEach(tarefa -> result.add(tarefaMapper.toResponseDTO(tarefa)));
        media.forEach(tarefa -> result.add(tarefaMapper.toResponseDTO(tarefa)));
        baixa.forEach(tarefa -> result.add(tarefaMapper.toResponseDTO(tarefa)));

        return result;
    }

    public TarefaResponse update(Long id, TarefaRequest dto) {
        Tarefa tarefa = tarefaRepository.findById(id).orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        tarefa.setNome(dto.nome());
        tarefa.setPrazo(dto.prazo());
        tarefa.setPrioridade(Prioridade.valueOf(dto.prioridade().toLowerCase()));
        tarefa.setStatus(Status.valueOf(dto.status().toLowerCase()));
        return tarefaMapper.toResponseDTO(tarefaRepository.save(tarefa));
    }

    public void delete(Long id) {
        tarefaRepository.deleteById(id);
    }

}
