package com.curie.curieapp.dto.request;

import java.time.LocalDateTime;

public record MembroForumRequest(Integer forumId, Integer userId, LocalDateTime entrada, LocalDateTime ultimaAparicao) {
}
