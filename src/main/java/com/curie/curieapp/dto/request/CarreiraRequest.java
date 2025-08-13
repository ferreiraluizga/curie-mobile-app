package com.curie.curieapp.dto.request;

import com.curie.curieapp.entities.Graduacao;
import com.curie.curieapp.entities.PosGraduacao;
import com.curie.curieapp.entities.Profissao;
import com.curie.curieapp.entities.User;

public record CarreiraRequest(User user, String descricao, Profissao profissao, Graduacao graduacao, PosGraduacao posGraduacao) {
}
