package com.curie.curieapp.dto.response;

import com.curie.curieapp.entities.AreaCarreira;

public record GraduacaoResponse(Integer id, String nome, String descricao, int duracao, AreaCarreira areaCarreira) {
}
