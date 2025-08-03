package com.curie.curieapp.dto.response;

import java.time.LocalDateTime;

public record TarefaResponse(Integer id, Integer userId, String nome, LocalDateTime prazo, String prioridade, String status) {
}
