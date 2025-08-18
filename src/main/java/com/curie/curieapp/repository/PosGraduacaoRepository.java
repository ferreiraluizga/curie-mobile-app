package com.curie.curieapp.repository;

import com.curie.curieapp.entities.PosGraduacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PosGraduacaoRepository extends JpaRepository<PosGraduacao, Long> {
}
