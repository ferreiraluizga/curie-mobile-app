package com.curie.curieapp.repository;


import com.curie.curieapp.entities.Forum;
import com.curie.curieapp.entities.Mensagens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MensagensRepository extends JpaRepository<Mensagens, Long>  {
    @Query(value = "SELECT * FROM mensagens WHERE forum_id = :forumId", nativeQuery = true)
    List<Forum> buscarMensagemPorForum(
            @Param("forumId") Long forumId
    );

}
