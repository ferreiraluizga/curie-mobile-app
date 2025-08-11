package com.curie.curieapp.mapper;

import com.curie.curieapp.dto.request.TipoAssinaturaRequest;
import com.curie.curieapp.dto.response.TipoAssinaturaResponse;
import com.curie.curieapp.entities.TipoAssinatura;
import com.curie.curieapp.entities.User;
import org.springframework.stereotype.Component;

@Component
public class TipoAssinaturaMapper {
    public TipoAssinaturaResponse toResponseDTO(TipoAssinatura tipoAssinatura) {
        return new TipoAssinaturaResponse(
                tipoAssinatura.getId(),
                tipoAssinatura.getUser().getId(),
                tipoAssinatura.getNome(),
                tipoAssinatura.getDescricao(),
                tipoAssinatura.getValor()
        );
    }

    public TipoAssinatura toEntity(TipoAssinaturaRequest dto) {
        TipoAssinatura tipoAssinatura = new TipoAssinatura();

        User user = new User();
        user.setId(dto.userId());

        tipoAssinatura.setUser(user);
        tipoAssinatura.setNome(dto.nome());
        tipoAssinatura.setDescricao(dto.descricao());
        tipoAssinatura.setValor(dto.valor());

        return tipoAssinatura;
    }


}
