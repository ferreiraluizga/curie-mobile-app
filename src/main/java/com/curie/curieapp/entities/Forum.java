package com.curie.curieapp.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "forum")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Forum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "categorias_id", nullable = false)
    private Categorias categorias;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private LocalDateTime criacao;

}
