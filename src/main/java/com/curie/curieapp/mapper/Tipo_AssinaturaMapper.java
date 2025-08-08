package com.curie.curieapp.mapper;

import com.curie.curieapp.dto.request.Tipo_AssinaturaRequest;
import com.curie.curieapp.dto.response.Tipo_AssinaturaResponse;
import com.curie.curieapp.entities.Tipo_Assinatura;
import com.curie.curieapp.entities.User;
import org.springframework.stereotype.Component;

@Component
public class Tipo_AssinaturaMapper {
    public Tipo_AssinaturaResponse toResponseDTO(Tipo_Assinatura tipo_assinatura) {
        return new Tipo_AssinaturaResponse(
                tipo_assinatura.getId(),
                tipo_assinatura.getUser().getId(),
                tipo_assinatura.getTipo(),
                tipo_assinatura.getDesc(),
                tipo_assinatura.getValor()
        );
    }

    public Tipo_Assinatura toEntity(Tipo_AssinaturaRequest dto) {
        Tipo_Assinatura tipo_assinatura = new Tipo_Assinatura();

        User user = new User();
        user.setId(dto.userId());

        tipo_assinatura.setUser(user);
        tipo_assinatura.setTipo(dto.tipo());
        tipo_assinatura.setDesc(dto.desc());
        tipo_assinatura.setValor(dto.valor());

        return tipo_assinatura;
    }


}
