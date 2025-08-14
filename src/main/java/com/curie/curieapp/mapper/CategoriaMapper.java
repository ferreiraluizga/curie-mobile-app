package com.curie.curieapp.mapper;


import com.curie.curieapp.dto.request.CategoriaRequest;
import com.curie.curieapp.dto.response.CategoriaResponse;
import com.curie.curieapp.entities.Categoria;
import com.curie.curieapp.entities.User;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {
    public CategoriaResponse toResponseDTO(Categoria categoria) {
        return new CategoriaResponse(
                categoria.getId(),
                categoria.getNome(),
                categoria.getDescricao()
        );
    }

    public Categoria toEntity(CategoriaRequest dto) {
        Categoria categoria = new Categoria();

        categoria.setNome(dto.nome());
        categoria.setDescricao(dto.descricao());

        return categoria;
    }


}