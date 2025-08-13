package com.curie.curieapp.dto.response;

import java.time.LocalDateTime;

public record ForumResponse(Integer id, Integer categoriasId, String nome, LocalDateTime criacao) {
}