package com.curie.curieapp.dto.request;

import java.time.LocalDateTime;

public record AssinaturaRequest(Integer userId, String cpf, LocalDateTime data_compra, String pagamento, Integer tipoId) {
}


