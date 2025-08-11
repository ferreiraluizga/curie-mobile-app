package com.curie.curieapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "membros_equipe")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MembroEquipe {

    @Column(nullable = false)
    private User user;

    @Column(nullable = false)
    private Equipe equipe;

}
