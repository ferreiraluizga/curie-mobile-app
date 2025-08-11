package com.curie.curieapp.mapper;

import com.curie.curieapp.dto.request.MembroEquipeRequest;
import com.curie.curieapp.dto.response.MembroEquipeResponse;
import com.curie.curieapp.entities.Equipe;
import com.curie.curieapp.entities.MembroEquipe;
import com.curie.curieapp.entities.User;
import org.springframework.stereotype.Component;

@Component
public class MembroEquipeMapper {

    public MembroEquipeResponse toResponseDTO(MembroEquipe membroEquipe) {
        return new MembroEquipeResponse(
                membroEquipe.getUser(),
                membroEquipe.getEquipe()
        );
    }

    public MembroEquipe toEntity(MembroEquipeRequest dto) {
        MembroEquipe membroEquipe = new MembroEquipe();

        User user = new User();
        user.setId(dto.user().getId());

        Equipe equipe = new Equipe();
        equipe.setId(dto.equipe().getId());

        membroEquipe.setEquipe(equipe);
        membroEquipe.setUser(user);

        return membroEquipe;
    }
}
