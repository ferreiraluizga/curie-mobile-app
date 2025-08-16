package com.curie.curieapp.dto.response;

import java.time.LocalDateTime;

public record MensagensResponse(Integer id, Integer forumId, Integer userId, LocalDateTime data, String texto, String arquivo) {
}
