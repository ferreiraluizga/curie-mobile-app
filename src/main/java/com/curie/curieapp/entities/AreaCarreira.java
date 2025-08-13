package com.curie.curieapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "areas_carreira")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AreaCarreira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String descricao;

}
