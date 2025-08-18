package com.curie.curieapp.mapper;

import com.curie.curieapp.dto.request.CarreiraRequest;
import com.curie.curieapp.dto.response.CarreiraResponse;
import com.curie.curieapp.entities.*;
import org.springframework.stereotype.Component;

@Component
public class CarreiraMapper {

    public CarreiraResponse toResponseDTO(Carreira carreira) {
        return new CarreiraResponse(
                carreira.getId(),
                carreira.getUser(),
                carreira.getDescricao(),
                carreira.getProfissao(),
                carreira.getGraduacao(),
                carreira.getPosGraduacao()
        );
    }

    public Carreira toEntity(CarreiraRequest dto) {
        Carreira carreira = new Carreira();

        User user = new User();
        user.setId(dto.user().getId());

        Profissao profissao = new Profissao();
        profissao.setId(dto.profissao().getId());

        Graduacao graduacao = new Graduacao();
        graduacao.setId(dto.graduacao().getId());

        PosGraduacao posGraduacao = new PosGraduacao();
        posGraduacao.setId(dto.posGraduacao().getId());

        carreira.setUser(user);
        carreira.setDescricao(dto.descricao());
        carreira.setProfissao(profissao);
        carreira.setGraduacao(graduacao);
        carreira.setPosGraduacao(posGraduacao);

        return carreira;
    }

}
