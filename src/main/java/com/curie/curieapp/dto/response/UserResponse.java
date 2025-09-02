package com.curie.curieapp.dto.response;

import java.time.LocalDateTime;

public record UserResponse(Long id, String name, String email, String password, String descricao, LocalDateTime nascimento, String telefone) {
}
