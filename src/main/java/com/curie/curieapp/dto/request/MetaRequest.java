package com.curie.curieapp.dto.request;

import java.time.LocalDateTime;

public record MetaRequest(Integer userId, String objetivo, String descricao, LocalDateTime inicio, LocalDateTime fim, String prioridade, String status) {
}
