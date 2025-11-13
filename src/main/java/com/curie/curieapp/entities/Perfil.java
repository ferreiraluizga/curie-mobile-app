package com.curie.curieapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "perfis")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "comportamento_id", nullable = true)
    private Comportamento comportamento;

    @ManyToOne
    @JoinColumn(name = "temperamento_id", nullable = true)
    private Temperamento temperamento;

    @ManyToOne
    @JoinColumn(name = "forca_educacional_id", nullable = true)
    private AreaConhecimento forca;

    @ManyToOne
    @JoinColumn(name = "fraqueza_educacional_id", nullable = true)
    private AreaConhecimento fraqueza;
}
