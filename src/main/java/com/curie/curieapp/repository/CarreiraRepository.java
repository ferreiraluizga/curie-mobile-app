package com.curie.curieapp.repository;

import com.curie.curieapp.entities.Carreira;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarreiraRepository extends JpaRepository<Carreira, Long> {
    @Query(value = "SELECT * FROM carreiras WHERE user_id = :userId", nativeQuery = true)
    List<Carreira> buscarPlanoPorId(
            @Param("userId") Long userId
    );
}