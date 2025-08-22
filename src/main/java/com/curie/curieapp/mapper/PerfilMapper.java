package com.curie.curieapp.mapper;


import com.curie.curieapp.dto.request.PerfilRequest;
import com.curie.curieapp.dto.response.PerfilResponse;
import com.curie.curieapp.entities.*;
import org.springframework.stereotype.Component;

@Component
public class PerfilMapper {
    public PerfilResponse toResponseDTO(Perfil perfil) {
        return new PerfilResponse(
                perfil.getId(),
                perfil.getUser(),
                perfil.getDescricao(),
                perfil.getComportamento(),
                perfil.getTemperamento(),
                perfil.getForca(),
                perfil.getFraqueza()
        );
    }

    public Perfil toEntity(PerfilRequest dto) {
        Perfil perfil = new Perfil();


        perfil.setUser(dto.user());
        perfil.setDescricao(dto.descricao());
        perfil.setComportamento(dto.comportamento());
        perfil.setTemperamento(dto.temperamento());
        perfil.setForca(dto.forca());
        perfil.setFraqueza(dto.fraqueza());

        return perfil;
    }
}
