package com.curie.curieapp.dto.response;

import java.time.LocalDateTime;

public record MembroForumResponse(Integer id, Integer forumId, Integer userId, LocalDateTime entrada, LocalDateTime ultimaAparicao) {
}
