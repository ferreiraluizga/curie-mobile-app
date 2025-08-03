package com.curie.curieapp.dto.request;

import java.time.LocalDateTime;

public record TarefaRequest(Integer userId, String nome, LocalDateTime prazo, String prioridade, String status) {
}
