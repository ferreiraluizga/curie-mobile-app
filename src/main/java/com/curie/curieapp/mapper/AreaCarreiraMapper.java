package com.curie.curieapp.mapper;

import com.curie.curieapp.dto.request.AreaCarreiraRequest;
import com.curie.curieapp.dto.response.AreaCarreiraResponse;
import com.curie.curieapp.entities.AreaCarreira;
import org.springframework.stereotype.Component;

@Component
public class AreaCarreiraMapper {

    public AreaCarreiraResponse toResponseDTO(AreaCarreira areaCarreira) {
        return new AreaCarreiraResponse(
                areaCarreira.getId(),
                areaCarreira.getNome(),
                areaCarreira.getDescricao()
        );
    }

    public AreaCarreira toEntity(AreaCarreiraRequest dto) {
        AreaCarreira areaCarreira = new AreaCarreira();

        areaCarreira.setNome(dto.nome());
        areaCarreira.setDescricao(dto.descricao());

        return areaCarreira;
    }

}
