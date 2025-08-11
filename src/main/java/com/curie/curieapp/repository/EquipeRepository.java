package com.curie.curieapp.repository;

import com.curie.curieapp.entities.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long> {

    @Query(value = "SELECT COUNT(user_id) AS membros FROM membros_equipe WHERE equipe_id = :id", nativeQuery = true)
    int quantidadeMembros(@Param("id") Long equipeId);

}
