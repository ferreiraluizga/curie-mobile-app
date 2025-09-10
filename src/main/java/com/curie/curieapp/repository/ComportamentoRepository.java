package com.curie.curieapp.repository;

import com.curie.curieapp.entities.Comportamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ComportamentoRepository extends JpaRepository<Comportamento, Long> {

    @Query(value = "DELETE * FROM comportamentos where user_id = :userId", nativeQuery = true)
    void deleteByUsuario (
            @Param("userId") Long userId
    );

}
