package com.curie.curieapp.dto.request;

import java.time.LocalDateTime;

public record AssinaturaRequest(Integer userId, String tipo, String desc, Double valor) {
}
