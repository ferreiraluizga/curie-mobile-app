package com.curie.curieapp.mapper;


import com.curie.curieapp.dto.request.MembroForumRequest;
import com.curie.curieapp.dto.response.MembroForumResponse;
import com.curie.curieapp.entities.Forum;
import com.curie.curieapp.entities.MembroForum;
import com.curie.curieapp.entities.User;
import org.springframework.stereotype.Component;

@Component
public class MembroForumMapper {
    public MembroForumResponse toResponseDTO(MembroForum membroForum) {
        return new MembroForumResponse(
                membroForum.getId(),
                membroForum.getForum().getId(),
                membroForum.getUser().getId(),
                membroForum.getEntrada(),
                membroForum.getUltimaAparicao()
        );
    }

    public MembroForum toEntity(MembroForumRequest dto) {
        MembroForum membroForum = new MembroForum();

        Forum forum = new Forum();
        forum.setId(dto.userId());

        User user = new User();
        user.setId(dto.userId());

        membroForum.setForum(forum);
        membroForum.setUser(user);
        membroForum.setEntrada(dto.entrada());
        membroForum.setUltimaAparicao(dto.ultimaAparicao());

        return membroForum;
    }
}
