package com.curie.curieapp.mapper;


import com.curie.curieapp.dto.request.AreaConhecimentoRequest;
import com.curie.curieapp.dto.response.AreaConhecimentoResponse;
import com.curie.curieapp.entities.AreaConhecimento;
import org.springframework.stereotype.Component;

@Component
public class AreaConhecimentoMapper {
    public AreaConhecimentoResponse toResponseDTO(AreaConhecimento areaConhecimento) {
        return new AreaConhecimentoResponse(
                areaConhecimento.getId(),
                areaConhecimento.getNome(),
                areaConhecimento.getMaterias()
        );
    }

    public AreaConhecimento toEntity(AreaConhecimentoRequest dto) {
        AreaConhecimento areaConhecimento = new AreaConhecimento();

        areaConhecimento.setNome(dto.nome());
        areaConhecimento.setMaterias(dto.materias());

        return areaConhecimento;
    }
}
