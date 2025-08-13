package com.curie.curieapp.dto.request;

import java.time.LocalDateTime;

public record ForumRequest(Integer categoriasId, String nome, LocalDateTime criacao) {
}
