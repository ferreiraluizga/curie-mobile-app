package com.curie.curieapp.repository;


import com.curie.curieapp.entities.Tarefa;
import com.curie.curieapp.entities.enums.Prioridade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    @Query(value = "SELECT * FROM tarefas WHERE status = 'pendente' AND user_id = :userId", nativeQuery = true)
    List<Tarefa> buscarTarefasPorUsuario(
            @Param("userId") Long userId
    );

    @Query(value = "SELECT * FROM tarefas WHERE nome = :nome AND user_id = :userId", nativeQuery = true)
    List<Tarefa> buscarTarefasPorNome(
            @Param("nome") String nome,
            @Param("userId") Long userId
    );

    @Query(value = "SELECT * FROM tarefas WHERE user_id = :userId order by fim desc", nativeQuery = true)
    List<Tarefa> buscarTarefasPorPrazo(
            @Param("userId") Long userId
    );

    @Query(value = "SELECT * FROM tarefas WHERE prioridade = :prioridade AND user_id = :userId", nativeQuery = true)
    List<Tarefa> buscarTarefasPorPrioridade(
            @Param("prioridade") Prioridade prioridade,
            @Param("userId") Long userId
    );
}
