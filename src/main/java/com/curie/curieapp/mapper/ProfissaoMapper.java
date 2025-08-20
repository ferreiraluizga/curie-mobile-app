package com.curie.curieapp.mapper;

import com.curie.curieapp.dto.request.ProfissaoRequest;
import com.curie.curieapp.dto.response.ProfissaoResponse;
import com.curie.curieapp.entities.Profissao;
import com.curie.curieapp.entities.enums.Demanda;
import org.springframework.stereotype.Component;

@Component
public class ProfissaoMapper {

    public ProfissaoResponse toResponseDTO(Profissao profissao) {
        return new ProfissaoResponse(
                profissao.getId(),
                profissao.getNome(),
                profissao.getDescricao(),
                profissao.getSalario(),
                profissao.getDemanda().name()
        );
    }

    public Profissao toEntity(ProfissaoRequest dto) {
        Profissao profissao = new Profissao();

        profissao.setNome(dto.nome());
        profissao.setDescricao(dto.descricao());
        profissao.setSalario(dto.salario());
        profissao.setDemanda(Demanda.valueOf(dto.demanda().toLowerCase()));

        return profissao;
    }

}
