package com.curie.curieapp.repository;

import com.curie.curieapp.entities.Comportamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComportamentoRepository extends JpaRepository<Comportamento, Long> {
}
