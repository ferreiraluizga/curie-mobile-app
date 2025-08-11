package com.curie.curieapp.repository;

import com.curie.curieapp.entities.TipoAssinatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoAssinaturaRepository extends JpaRepository<TipoAssinatura, Long> {
}
