package com.curie.curieapp.dto.response;

import com.curie.curieapp.entities.Graduacao;
import com.curie.curieapp.entities.PosGraduacao;
import com.curie.curieapp.entities.Profissao;
import com.curie.curieapp.entities.User;

public record CarreiraResponse(Integer id, User user, String descricao, Integer profissaoId, Integer graduacaoId, Integer posGraduacaoId) {
}
