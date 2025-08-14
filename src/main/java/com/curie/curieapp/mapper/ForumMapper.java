package com.curie.curieapp.mapper;


import com.curie.curieapp.dto.request.ForumRequest;
import com.curie.curieapp.dto.response.ForumResponse;
import com.curie.curieapp.entities.Categoria;
import com.curie.curieapp.entities.Forum;
import org.springframework.stereotype.Component;

@Component
public class ForumMapper {
    public ForumResponse toResponseDTO(Forum forum) {
        return new ForumResponse(
                forum.getId(),
                forum.getCategoria().getId(),
                forum.getNome(),
                forum.getCriacao()
        );
    }

    public Forum toEntity(ForumRequest dto) {
        Forum forum = new Forum();

        Categoria categoria = new Categoria();
        categoria.setId(dto.categoriasId());

        forum.setCategoria(categoria);
        forum.setNome(dto.nome());
        forum.setCriacao(dto.criacao());

        return forum;
    }

}