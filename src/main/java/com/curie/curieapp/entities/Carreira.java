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

    @ManyToOne
    @JoinColumn(name = "profissao_id", nullable = false)
    private Profissao profissao;

    @ManyToOne
    @JoinColumn(name = "graduacao_id", nullable = false)
    private Graduacao graduacao;

    @ManyToOne
    @JoinColumn(name = "pos_graduacao_id", nullable = false)
    private PosGraduacao posGraduacao;

}
