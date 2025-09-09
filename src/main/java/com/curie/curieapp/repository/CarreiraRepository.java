package com.curie.curieapp.repository;

import com.curie.curieapp.entities.Carreira;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarreiraRepository extends JpaRepository<Carreira, Long> {
    @Query(value = "SELECT * FROM carreiras WHERE user_id = :userId", nativeQuery = true)
    List<Carreira> getByUsuario(
            @Param("userId") Long userId
    );

    @Query(value = "DELETE * FROM carreiras where user_id = :userId", nativeQuery = true)
    void deleteByUsuario (
            @Param("userId") Long userId
    );

    @Query(value = "SELECT * FROM carreiras WHERE user_id = :userId ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Carreira getMaisRecente(
            @Param("userId") Long userId
    );
}