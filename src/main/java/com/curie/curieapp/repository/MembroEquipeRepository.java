package com.curie.curieapp.repository;

import com.curie.curieapp.entities.MembroEquipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembroEquipeRepository extends JpaRepository<MembroEquipe, Long> {
    @Query(value = "SELECT * FROM membros_equipe WHERE equipe_id = :id", nativeQuery = true)
    List<MembroEquipe> membrosEquipe(@Param("id") Long equipeId);
}
