package com.curie.curieapp.mapper;


import com.curie.curieapp.dto.request.TemperamentoRequest;
import com.curie.curieapp.dto.response.TemperamentoResponse;
import com.curie.curieapp.entities.Temperamento;
import com.curie.curieapp.entities.TipoTemperamento;
import org.springframework.stereotype.Component;

@Component
public class TemperamentoMapper {
    public TemperamentoResponse toResponseDTO(Temperamento temperamento) {
        return new TemperamentoResponse(
                temperamento.getId(),
                temperamento.getTipoTemperamento().getId(),
                temperamento.getForcaAprendizado(),
                temperamento.getFraquezaAprendizado()
        );
    }

    public Temperamento toEntity(TemperamentoRequest dto) {
        Temperamento temperamento = new Temperamento();

        TipoTemperamento tipoTemperamento = new TipoTemperamento();
        tipoTemperamento.setId(dto.tipoTemperamentoId());

        temperamento.setTipoTemperamento(tipoTemperamento);
        temperamento.setForcaAprendizado(dto.forcaAprendizado());
        temperamento.setFraquezaAprendizado(dto.fraquezaAprendizado());

        return temperamento;
    }
}
