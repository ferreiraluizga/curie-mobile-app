package com.curie.curieapp.mapper;


import com.curie.curieapp.dto.request.TipoTemperamentoRequest;
import com.curie.curieapp.dto.response.TipoTemperamentoResponse;
import com.curie.curieapp.entities.TipoTemperamento;
import org.springframework.stereotype.Component;

@Component
public class TipoTemperamentoMapper {
    public TipoTemperamentoResponse toResponseDTO(TipoTemperamento tipoTemperamento) {
        return new TipoTemperamentoResponse(
                tipoTemperamento.getId(),
                tipoTemperamento.getNome(),
                tipoTemperamento.getDescricao()
        );
    }

    public TipoTemperamento toEntity(TipoTemperamentoRequest dto) {
        TipoTemperamento tipoTemperamento = new TipoTemperamento();

        tipoTemperamento.setNome(dto.nome());
        tipoTemperamento.setDescricao(dto.descricao());

        return tipoTemperamento;
    }
}
