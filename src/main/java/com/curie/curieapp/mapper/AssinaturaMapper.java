package com.curie.curieapp.mapper;

import com.curie.curieapp.dto.request.AssinaturaRequest;
import com.curie.curieapp.dto.response.AssinaturaResponse;
import com.curie.curieapp.entities.Assinatura;
import com.curie.curieapp.entities.Tipo_Assinatura;
import com.curie.curieapp.entities.User;
import org.springframework.stereotype.Component;

@Component
public class AssinaturaMapper {

    public AssinaturaResponse toResponseDTO(Assinatura assinatura) {
        return new AssinaturaResponse(
                assinatura.getId(),
                assinatura.getUser().getId(),
                assinatura.getCpf(),
                assinatura.getData_compra(),
                assinatura.getPagamento().name(),
                assinatura.getTipo_assinatura().getId()
        );
    }
    public Assinatura toEntity(AssinaturaRequest dto) {
        Assinatura assinatura = new Assinatura();

        User user = new User();
        user.setId(dto.userId());

        Tipo_Assinatura tipo_assinatura = new Tipo_Assinatura();
        tipo_assinatura.setId(dto.tipoId());

        assinatura.setUser(user);
        assinatura.setCpf(dto.cpf());
        assinatura.setData_compra(dto.data_compra());
        assinatura.setPagamento(Assinatura.Pagamento.valueOf(dto.pagamento().toLowerCase()));
        assinatura.setTipo_assinatura(tipo_assinatura);

        return assinatura;
    }
}

