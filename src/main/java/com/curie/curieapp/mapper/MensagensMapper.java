package com.curie.curieapp.mapper;


import com.curie.curieapp.dto.request.MensagensRequest;
import com.curie.curieapp.dto.response.MensagensResponse;
import com.curie.curieapp.entities.Forum;
import com.curie.curieapp.entities.Mensagens;
import com.curie.curieapp.entities.User;
import org.springframework.stereotype.Component;

@Component
public class MensagensMapper {
    public MensagensResponse toResponseDTO(Mensagens mensagens) {
        return new MensagensResponse(
                mensagens.getId(),
                mensagens.getForum().getId(),
                mensagens.getUser().getId(),
                mensagens.getData(),
                mensagens.getTexto(),
                mensagens.getArquivo()
        );
    }

    public Mensagens toEntity(MensagensRequest dto) {
        Mensagens mensagens = new Mensagens();

        Forum forum = new Forum();
        forum.setId(dto.userId());

        User user = new User();
        user.setId(dto.userId());

        mensagens.setForum(forum);
        mensagens.setUser(user);
        mensagens.setData(dto.data());
        mensagens.setTexto(dto.texto());
        mensagens.setArquivo(dto.arquivo());

        return mensagens;
    }
}
