package com.curie.curieapp.repository;

import com.curie.curieapp.entities.Meta;
import com.curie.curieapp.entities.enums.Prioridade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetaRepository extends JpaRepository<Meta, Long> {

    @Query("SELECT * FROM metas WHERE status = 'pendente' AND user_id = :userId")
    List<Meta> buscarMetasPorUsuario(
            @Param("userId") Long userId
    );

    @Query("SELECT * FROM metas WHERE nome = :nome AND user_id = :userId")
    List<Meta> buscarMetasPorNome(
            @Param("nome") String nome,
            @Param("userId") Long userId
    );

    @Query("SELECT * FROM metas WHERE user_id = :userId order by fim desc")
    List<Meta> buscarMetasPorPrazo(
            @Param("userId") Long userId
    );

    @Query("SELECT * FROM metas WHERE prioridade = :prioridade AND user_id = :userId")
    List<Meta> buscarMetasPorPrioridade(
            @Param("prioridade") Prioridade prioridade,
            @Param("userId") Long userId
    );

}
