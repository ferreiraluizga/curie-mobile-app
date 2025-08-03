package com.curie.curieapp.mapper;

import com.curie.curieapp.dto.request.TarefaRequest;
import com.curie.curieapp.dto.response.TarefaResponse;
import com.curie.curieapp.entities.Tarefa;
import com.curie.curieapp.entities.User;
import org.springframework.stereotype.Component;

@Component
public class TarefaMapper {

    public TarefaResponse toResponseDTO(Tarefa tarefa) {
        return new TarefaResponse(
                tarefa.getId(),
                tarefa.getUser().getId(),
                tarefa.getNome(),
                tarefa.getPrazo(),
                tarefa.getPrioridade().name(),
                tarefa.getStatus().name()
        );
    }

    public Tarefa toEntity(TarefaRequest dto) {
        Tarefa tarefa = new Tarefa();

        User user = new User();
        user.setId(dto.userId());

        tarefa.setUser(user);
        tarefa.setNome(dto.nome());
        tarefa.setPrazo(dto.prazo());
        tarefa.setPrioridade(Tarefa.Prioridade.valueOf(dto.prioridade().toLowerCase()));
        tarefa.setStatus(Tarefa.Status.valueOf(dto.status().toLowerCase()));

        return tarefa;
    }

}
