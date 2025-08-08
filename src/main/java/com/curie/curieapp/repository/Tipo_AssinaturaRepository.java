package com.curie.curieapp.repository;

import com.curie.curieapp.entities.Tipo_Assinatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Tipo_AssinaturaRepository extends JpaRepository<Tipo_Assinatura, Long> {
}
