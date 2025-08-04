package com.curie.curieapp.dto.response;

import java.time.LocalDateTime;

public record MetaResponse(Integer id, Integer userId, String nome, String descricao, LocalDateTime inicio, LocalDateTime fim, String prioridade, String status) {
}
