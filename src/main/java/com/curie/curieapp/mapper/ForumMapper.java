package com.curie.curieapp.mapper;


import com.curie.curieapp.controller.ForumResponse;
import com.curie.curieapp.dto.request.ForumRequest;
import com.curie.curieapp.entities.Forum;

public class ForumMapper {
    public ForumResponse toResponseDTO(Forum forum) {
        return new ForumResponse(
                forum.getId(),
                forum.getCategorias().getId(),
                forum.getNome(),
                forum.getCriacao()
        );
    }

    public Forum toEntity(ForumRequest dto) {
        Forum forum = new Forum();

        Categorias categorias = new Categorias();
        categorias.setId(dto.userId());

        forum.setCategorias(categorias);
        forum.setNome(dto.nome());
        forum.setCriacao(dto.criacao());

        return forum;
    }

}