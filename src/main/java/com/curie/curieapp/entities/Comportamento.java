package com.curie.curieapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "comportamento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comportamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "tipoComportamento_id", nullable = false)
    private TipoComportamento tipoComportamento;

    @Column(nullable = false)
    private String caracteristicas;

    @Column(nullable = false)
    private String aprendizagem;

    @Column(nullable = false)
    private String descricaoEstudo;

}
