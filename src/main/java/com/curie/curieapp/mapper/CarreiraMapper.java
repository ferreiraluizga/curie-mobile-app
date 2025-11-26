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
                // CORRIGIDO: Mapeando o ID (pode ser null)
                carreira.getProfissao() != null ? carreira.getProfissao().getId() : null,
                carreira.getGraduacao() != null ? carreira.getGraduacao().getId() : null,
                carreira.getPosGraduacao() != null ? carreira.getPosGraduacao().getId() : null
        );
    }

    public Carreira toEntity(CarreiraRequest dto) {

        Carreira carreira = new Carreira();

        // 🔵 Apenas seta os IDs — sem carregar do banco no mapper
        User user = new User();
        user.setId(Math.toIntExact(dto.userId()));

        Profissao profissao = null;
        if (dto.profissaoId() != null) {
            profissao = new Profissao();
            profissao.setId(Math.toIntExact(dto.profissaoId()));
        }

        Graduacao graduacao = null;
        if (dto.graduacaoId() != null) {
            graduacao = new Graduacao();
            graduacao.setId(Math.toIntExact(dto.graduacaoId()));
        }

        PosGraduacao pos = null;
        if (dto.posGraduacaoId() != null) {
            pos = new PosGraduacao();
            pos.setId(Math.toIntExact(dto.posGraduacaoId()));
        }

        carreira.setUser(user);
        carreira.setDescricao(dto.descricao());
        carreira.setProfissao(profissao);
        carreira.setGraduacao(graduacao);
        carreira.setPosGraduacao(pos);

        return carreira;
    }
}
