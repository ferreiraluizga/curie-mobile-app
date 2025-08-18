package com.curie.curieapp.repository;

import com.curie.curieapp.entities.Graduacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GraduacaoRepository extends JpaRepository<Graduacao, Long> {
}
