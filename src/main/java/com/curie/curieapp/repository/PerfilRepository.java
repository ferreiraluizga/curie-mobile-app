package com.curie.curieapp.repository;

import com.curie.curieapp.entities.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    @Query(value = "SELECT * FROM perfis WHERE user_id = :userId", nativeQuery = true)
    List<Perfil> getByUsuario(
            @Param("userId") Long userId
    );

    @Query(value = "SELECT * FROM perfis WHERE user_id = :userId ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Perfil getMaisRecente(
            @Param("userId") Long userId
    );

    @Query(value = "DELETE * FROM perfis where user_id = :userId", nativeQuery = true)
    void deleteByUsuario (
            @Param("userId") Long userId
    );
}




