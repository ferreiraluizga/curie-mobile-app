package com.curie.curieapp.mapper;

import com.curie.curieapp.dto.request.AssinaturaRequest;
import com.curie.curieapp.dto.response.AssinaturaResponse;
import com.curie.curieapp.entities.Assinatura;
import com.curie.curieapp.entities.User;
import org.springframework.stereotype.Component;

@Component
public class AssinaturaMapper {
    public AssinaturaResponse toResponseDTO(Assinatura assinatura) {
        return new AssinaturaResponse(
                assinatura.getId(),
                assinatura.getUser().getId(),
                assinatura.getTipo(),
                assinatura.getDesc(),
                assinatura.getValor()
        );
    }

    public Assinatura toEntity(AssinaturaRequest dto) {
        Assinatura assinatura = new Assinatura();

        User user = new User();
        user.setId(dto.userId());

        assinatura.setUser(user);
        assinatura.setTipo(dto.tipo());
        assinatura.setDesc(dto.desc());
        assinatura.setValor(dto.valor());

        return assinatura;
    }


}
