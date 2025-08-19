package com.curie.curieapp.mapper;


import com.curie.curieapp.dto.request.TipoComportamentoRequest;
import com.curie.curieapp.dto.response.TipoComportamentoResponse;
import com.curie.curieapp.entities.TipoComportamento;
import org.springframework.stereotype.Component;

@Component
public class TipoComportamentoMapper {
    public TipoComportamentoResponse toResponseDTO(TipoComportamento tipoComportamento) {
        return new TipoComportamentoResponse(
                tipoComportamento.getId(),
                tipoComportamento.getNome(),
                tipoComportamento.getDescricao()
        );
    }

    public TipoComportamento toEntity(TipoComportamentoRequest dto) {
        TipoComportamento tipoComportamento = new TipoComportamento();

        tipoComportamento.setNome(dto.nome());
        tipoComportamento.setDescricao(dto.descricao());

        return tipoComportamento;
    }
}
