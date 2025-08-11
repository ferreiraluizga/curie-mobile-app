package com.curie.curieapp.dto.response;

import com.curie.curieapp.entities.TipoAssinatura;

import java.time.LocalDateTime;

public record AssinaturaResponse(Integer id, Integer userId, String cpf, LocalDateTime dataCompra, String pagamento, TipoAssinatura tipoAssinatura) {
}
