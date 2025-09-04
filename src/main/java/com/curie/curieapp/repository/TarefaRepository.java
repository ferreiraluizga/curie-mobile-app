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

    @Query(value = "SELECT * FROM tarefas WHERE status LIKE 'pendente' AND user_id = :userId", nativeQuery = true)
    List<Tarefa> getByUsuario(
            @Param("userId") Long userId
    );

    @Query(value = "SELECT * FROM tarefas WHERE nome LIKE :nome AND user_id = :userId", nativeQuery = true)
    List<Tarefa> getByNome(
            @Param("nome") String nome,
            @Param("userId") Long userId
    );

    @Query(value = "SELECT * FROM tarefas WHERE user_id = :userId ORDER BY prazo DESC", nativeQuery = true)
    List<Tarefa> getByPrazo(
            @Param("userId") Long userId
    );

    @Query(value = "SELECT * FROM tarefas WHERE prioridade LIKE :prioridade AND user_id = :userId", nativeQuery = true)
    List<Tarefa> getByPrioridade(
            @Param("prioridade") String prioridade,
            @Param("userId") Long userId
    );

}
