package com.curie.curieapp.dto.request;

import java.time.LocalDateTime;

public record MensagensRequest(Integer forumId, Integer userId, LocalDateTime data, String texto, String arquivo) {
}
