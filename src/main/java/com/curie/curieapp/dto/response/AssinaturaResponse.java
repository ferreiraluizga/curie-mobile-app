package com.curie.curieapp.dto.response;

import java.time.LocalDateTime;

public record AssinaturaResponse(Integer id, Integer userId, String cpf, LocalDateTime data_compra, String pagamento, Integer tipoId ) {
}
