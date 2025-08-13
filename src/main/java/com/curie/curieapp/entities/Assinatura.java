package com.curie.curieapp.entities;

import com.curie.curieapp.entities.enums.Pagamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "assinaturas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Assinatura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false)
    private LocalDateTime dataCompra;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Pagamento pagamento;

    @ManyToOne
    @JoinColumn(name = "tipo_assinatura_id", nullable = false)
    private TipoAssinatura tipoAssinatura;
}
