package com.curie.curieapp.mapper;

import com.curie.curieapp.dto.request.MetaRequest;
import com.curie.curieapp.dto.request.TarefaRequest;
import com.curie.curieapp.dto.response.MetaResponse;
import com.curie.curieapp.entities.Meta;
import com.curie.curieapp.entities.Tarefa;
import com.curie.curieapp.entities.User;
import com.curie.curieapp.entities.enums.Prioridade;
import com.curie.curieapp.entities.enums.Status;
import org.springframework.stereotype.Component;

@Component
public class MetaMapper {

    public MetaResponse toResponseDTO(Meta meta) {
        return new MetaResponse(
                meta.getId(),
                meta.getUser().getId(),
                meta.getObjetivo(),
                meta.getDescricao(),
                meta.getInicio(),
                meta.getFim(),
                meta.getPrioridade().name(),
                meta.getStatus().name()
        );
    }

    public Meta toEntity(MetaRequest dto) {
        Meta meta = new Meta();

        User user = new User();
        user.setId(dto.userId());

        meta.setUser(user);
        meta.setObjetivo(dto.objetivo());
        meta.setDescricao(dto.descricao());
        meta.setInicio(dto.inicio());
        meta.setFim(dto.fim());
        meta.setPrioridade(Prioridade.valueOf(dto.prioridade().toLowerCase()));
        meta.setStatus(Status.valueOf(dto.status().toLowerCase()));

        return meta;
    }

}
