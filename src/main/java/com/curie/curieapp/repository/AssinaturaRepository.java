package com.curie.curieapp.repository;

import com.curie.curieapp.entities.Assinatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssinaturaRepository extends  JpaRepository<Assinatura, Long>{

}
