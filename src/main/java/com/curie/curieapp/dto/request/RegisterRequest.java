package com.curie.curieapp.dto.request;

import java.time.LocalDateTime;

public record RegisterRequest(String name, String email, String password, String descricao, LocalDateTime nascimento, String telefone) {
}
