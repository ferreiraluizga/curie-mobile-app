package com.curie.curieapp.dto.request;

import com.curie.curieapp.entities.AreaCarreira;

public record PosGraduacaoRequest(String nome, String descricao, int duracao, AreaCarreira areaCarreira) {
}
