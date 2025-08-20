package com.curie.curieapp.repository;


import com.curie.curieapp.entities.Temperamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemperamentoRepository extends JpaRepository<Temperamento, Long> {
}
