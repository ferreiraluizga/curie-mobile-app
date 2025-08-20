package com.curie.curieapp.mapper;


import com.curie.curieapp.dto.request.ComportamentoRequest;
import com.curie.curieapp.dto.response.ComportamentoResponse;
import com.curie.curieapp.entities.Comportamento;
import com.curie.curieapp.entities.TipoComportamento;
import org.springframework.stereotype.Component;

@Component
public class ComportamentoMapper {
    public ComportamentoResponse toResponseDTO(Comportamento comportamento) {
        return new ComportamentoResponse(
                comportamento.getId(),
                comportamento.getTipoComportamento().getId(),
                comportamento.getCaracteristicas(),
                comportamento.getAprendizagem(),
                comportamento.getDescricaoEstudo()
        );
    }

    public Comportamento toEntity(ComportamentoRequest dto) {
        Comportamento comportamento = new Comportamento();

        TipoComportamento tipoComportamento = new TipoComportamento();
        tipoComportamento.setId(dto.tipoComportamentoId());

        comportamento.setTipoComportamento(tipoComportamento);
        comportamento.setCaracteristicas(dto.caracteristicas());
        comportamento.setAprendizagem(dto.aprendizagem());
        comportamento.setDescricaoEstudo(dto.descricaoEstudo());

        return comportamento;
    }
}
