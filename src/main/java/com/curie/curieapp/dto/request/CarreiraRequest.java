package com.curie.curieapp.dto.request;

import com.curie.curieapp.entities.Graduacao;
import com.curie.curieapp.entities.PosGraduacao;
import com.curie.curieapp.entities.Profissao;
import com.curie.curieapp.entities.User;

public record CarreiraRequest(Long userId, String descricao, Long profissaoId, Long graduacaoId, Long posGraduacaoId) {
}
