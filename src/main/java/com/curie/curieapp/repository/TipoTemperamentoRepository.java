package com.curie.curieapp.repository;


import com.curie.curieapp.entities.TipoTemperamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoTemperamentoRepository extends JpaRepository<TipoTemperamento, Long> {
}
