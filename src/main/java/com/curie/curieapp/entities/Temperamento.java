package com.curie.curieapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "temperamento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Temperamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "tipoTemperamento_id", nullable = false)
    private TipoTemperamento tipoTemperamento;

    @Column(nullable = false)
    private String forcaAprendizado;

    @Column(nullable = false)
    private String fraquezaAprendizado;
}
