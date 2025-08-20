package com.curie.curieapp.mapper;

import com.curie.curieapp.dto.request.PosGraduacaoRequest;
import com.curie.curieapp.dto.response.PosGraduacaoResponse;
import com.curie.curieapp.entities.AreaCarreira;
import com.curie.curieapp.entities.PosGraduacao;
import org.springframework.stereotype.Component;

@Component
public class PosGraduacaoMapper {

    public PosGraduacaoResponse toResponseDTO(PosGraduacao posGraduacao) {
        return new PosGraduacaoResponse(
                posGraduacao.getId(),
                posGraduacao.getNome(),
                posGraduacao.getDescricao(),
                posGraduacao.getDuracao(),
                posGraduacao.getAreaCarreira()
        );
    }

    public PosGraduacao toEntity(PosGraduacaoRequest dto) {
        PosGraduacao posGraduacao = new PosGraduacao();

        AreaCarreira areaCarreira = new AreaCarreira();
        areaCarreira.setId(dto.areaCarreira().getId());

        posGraduacao.setNome(dto.nome());
        posGraduacao.setDescricao(dto.descricao());
        posGraduacao.setDuracao(dto.duracao());
        posGraduacao.setAreaCarreira(areaCarreira);

        return posGraduacao;
    }

}
