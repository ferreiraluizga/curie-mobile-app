package com.curie.curieapp.dto.response;

import java.time.LocalDateTime;

public record AssinaturaResponse(Integer id, Integer userId, String tipo, String desc, Double valor) {
}
