package com.curie.curieapp.repository;

import com.curie.curieapp.entities.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    @Query(value = "SELECT * FROM perfil WHERE user_id = :userId", nativeQuery = true)
    List<Perfil> buscarPerfilPorUsuario(
            @Param("userId") Long userId
    );
}




