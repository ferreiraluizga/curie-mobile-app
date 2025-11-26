package com.curie.curieapp.dto.response;

import com.curie.curieapp.entities.AreaConhecimento;
import com.curie.curieapp.entities.Comportamento;
import com.curie.curieapp.entities.Temperamento;
import com.curie.curieapp.entities.User;

public record PerfilResponse(Integer id, User user, String descricao, Integer comportamentoId, Integer temperamentoId, Integer forcaId, Integer fraquezaId) {
}
