package com.curie.curieapp.mapper;

import com.curie.curieapp.dto.request.GraduacaoRequest;
import com.curie.curieapp.dto.response.GraduacaoResponse;
import com.curie.curieapp.entities.AreaCarreira;
import com.curie.curieapp.entities.Graduacao;
import org.springframework.stereotype.Component;

@Component
public class GraduacaoMapper {

    public GraduacaoResponse toResponseDTO(Graduacao graduacao) {
        return new GraduacaoResponse(
                graduacao.getId(),
                graduacao.getNome(),
                graduacao.getDescricao(),
                graduacao.getDuracao(),
                graduacao.getAreaCarreira()
        );
    }

    public Graduacao toEntity(GraduacaoRequest dto) {
        Graduacao graduacao = new Graduacao();

        AreaCarreira areaCarreira = new AreaCarreira();
        areaCarreira.setId(dto.areaCarreira().getId());

        graduacao.setNome(dto.nome());
        graduacao.setDescricao(dto.descricao());
        graduacao.setDuracao(dto.duracao());
        graduacao.setAreaCarreira(areaCarreira);

        return graduacao;
    }

}
