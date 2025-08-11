package com.curie.curieapp.dto.response;

import com.curie.curieapp.entities.Equipe;
import com.curie.curieapp.entities.User;

public record MembroEquipeResponse(User user, Equipe equipe) {
}
