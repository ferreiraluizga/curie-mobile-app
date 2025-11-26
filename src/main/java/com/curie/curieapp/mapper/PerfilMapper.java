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

        // User
        User user = new User();
        user.setId(Math.toIntExact(dto.userId()));

        // Comportamento
        Comportamento comportamento = null;
        if (dto.comportamentoId() != null) {
            comportamento = new Comportamento();
            comportamento.setId(Math.toIntExact(dto.comportamentoId()));
        }

        // Temperamento
        Temperamento temperamento = null;
        if (dto.temperamentoId() != null) {
            temperamento = new Temperamento();
            temperamento.setId(Math.toIntExact(dto.temperamentoId()));
        }

        // Força
        AreaConhecimento forca = null;
        if (dto.forcaId() != null) {
            forca = new AreaConhecimento();
            forca.setId(Math.toIntExact(dto.forcaId()));
        }

        // Fraqueza
        AreaConhecimento fraqueza = null;
        if (dto.fraquezaId() != null) {
            fraqueza = new AreaConhecimento();
            fraqueza.setId(Math.toIntExact(dto.fraquezaId()));
        }

        perfil.setUser(user);
        perfil.setDescricao(dto.descricao());
        perfil.setComportamento(comportamento);
        perfil.setTemperamento(temperamento);
        perfil.setForca(forca);
        perfil.setFraqueza(fraqueza);

        return perfil;
    }
}
