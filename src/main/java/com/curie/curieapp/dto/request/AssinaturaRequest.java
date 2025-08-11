package com.curie.curieapp.dto.request;

import com.curie.curieapp.entities.TipoAssinatura;

import java.time.LocalDateTime;

public record AssinaturaRequest(Integer userId, String cpf, LocalDateTime dataCompra, String pagamento, TipoAssinatura tipoAssinatura) {
}


