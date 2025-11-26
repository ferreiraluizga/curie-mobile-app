package com.curie.curieapp.dto.request;

import com.curie.curieapp.entities.AreaConhecimento;
import com.curie.curieapp.entities.Comportamento;
import com.curie.curieapp.entities.Temperamento;
import com.curie.curieapp.entities.User;

public record PerfilRequest(Long userId, String descricao, Long comportamentoId, Long temperamentoId, Long forcaId, Long fraquezaId) {
}
