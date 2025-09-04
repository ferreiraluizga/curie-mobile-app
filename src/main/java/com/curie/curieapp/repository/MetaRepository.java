package com.curie.curieapp.repository;

import com.curie.curieapp.entities.Meta;
import com.curie.curieapp.entities.Tarefa;
import com.curie.curieapp.entities.enums.Prioridade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetaRepository extends JpaRepository<Meta, Long> {

    @Query(value = "SELECT * FROM metas WHERE status LIKE 'pendente' AND user_id = :userId", nativeQuery = true)
    List<Meta> getByUsuario(
            @Param("userId") Long userId
    );

    @Query(value = "SELECT * FROM metas WHERE nome LIKE :nome AND user_id = :userId", nativeQuery = true)
    List<Meta> getByNome(
            @Param("nome") String nome,
            @Param("userId") Long userId
    );

    @Query(value = "SELECT * FROM metas WHERE user_id = :userId ORDER BY prazo DESC", nativeQuery = true)
    List<Meta> getByPrazo(
            @Param("userId") Long userId
    );

    @Query(value = "SELECT * FROM metas WHERE prioridade LIKE :prioridade AND user_id = :userId", nativeQuery = true)
    List<Meta> getByPrioridade(
            @Param("prioridade") String prioridade,
            @Param("userId") Long userId
    );

}
