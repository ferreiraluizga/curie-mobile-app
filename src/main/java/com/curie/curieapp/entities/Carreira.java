package com.curie.curieapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "carreiras")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Carreira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private Profissao profissao;

    @Column(nullable = false)
    private Graduacao graduacao;

    @Column(nullable = false)
    private PosGraduacao posGraduacao;

}
