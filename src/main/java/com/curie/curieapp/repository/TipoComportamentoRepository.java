package com.curie.curieapp.repository;


import com.curie.curieapp.entities.TipoComportamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoComportamentoRepository extends JpaRepository<TipoComportamento, Long> {
}
