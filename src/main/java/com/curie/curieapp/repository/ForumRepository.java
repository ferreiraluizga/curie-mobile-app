package com.curie.curieapp.repository;

import com.curie.curieapp.entities.Forum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForumRepository extends JpaRepository<Forum, Long> {
    @Query(value = "SELECT * FROM foruns WHERE nome = :nome", nativeQuery = true)
    List<Forum> buscarForumPorNome(
            @Param("nome") String nome
    );
}
