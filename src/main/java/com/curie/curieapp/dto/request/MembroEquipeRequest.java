package com.curie.curieapp.dto.request;

import com.curie.curieapp.entities.Equipe;
import com.curie.curieapp.entities.User;

public record MembroEquipeRequest(User user, Equipe equipe) {
}
