package com.curie.curieapp.mapper;

import com.curie.curieapp.dto.request.AssinaturaRequest;
import com.curie.curieapp.dto.response.AssinaturaResponse;
import com.curie.curieapp.entities.Assinatura;
import com.curie.curieapp.entities.TipoAssinatura;
import com.curie.curieapp.entities.User;
import com.curie.curieapp.entities.enums.Pagamento;
import org.springframework.stereotype.Component;

@Component
public class AssinaturaMapper {

    public AssinaturaResponse toResponseDTO(Assinatura assinatura) {
        return new AssinaturaResponse(
                assinatura.getId(),
                assinatura.getUser().getId(),
                assinatura.getCpf(),
                assinatura.getDataCompra(),
                assinatura.getPagamento().name(),
                assinatura.getTipoAssinatura()
        );
    }
    public Assinatura toEntity(AssinaturaRequest dto) {
        Assinatura assinatura = new Assinatura();

        User user = new User();
        user.setId(dto.userId());

        TipoAssinatura tipoAssinatura = new TipoAssinatura();
        tipoAssinatura.setId(dto.tipoAssinatura().getId());

        assinatura.setUser(user);
        assinatura.setCpf(dto.cpf());
        assinatura.setDataCompra(dto.dataCompra());
        assinatura.setPagamento(Pagamento.valueOf(dto.pagamento().toLowerCase()));
        assinatura.setTipoAssinatura(tipoAssinatura);

        return assinatura;
    }
}

