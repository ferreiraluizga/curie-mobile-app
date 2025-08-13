package com.curie.curieapp.dto.response;

import com.curie.curieapp.entities.AreaCarreira;

public record PosGraduacaoResponse(Integer id, String nome, String descricao, int duracao, AreaCarreira areaCarreira) {
}
