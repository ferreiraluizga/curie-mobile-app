package com.curie.curieapp.mapper;

import com.curie.curieapp.dto.request.EquipeRequest;
import com.curie.curieapp.dto.response.EquipeResponse;
import com.curie.curieapp.entities.Assinatura;
import com.curie.curieapp.entities.Equipe;

public class EquipeMapper {

    public EquipeResponse toResponseDTO(Equipe equipe) {
        return new EquipeResponse(
                equipe.getId(),
                equipe.getAssinatura()
        );
    }

    public Equipe toEntity(EquipeRequest dto) {
        Equipe equipe = new Equipe();

        Assinatura assinatura = new Assinatura();
        assinatura.setId(dto.assinatura().getId());

        equipe.setAssinatura(assinatura);

        return equipe;
    }
}
