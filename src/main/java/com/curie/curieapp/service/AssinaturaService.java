package com.curie.curieapp.service;


import com.curie.curieapp.dto.request.AssinaturaRequest;
import com.curie.curieapp.dto.response.AssinaturaResponse;
import com.curie.curieapp.entities.Assinatura;
import com.curie.curieapp.entities.TipoAssinatura;
import com.curie.curieapp.entities.enums.Pagamento;
import com.curie.curieapp.mapper.AssinaturaMapper;
import com.curie.curieapp.repository.AssinaturaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AssinaturaService {

    @Autowired
    private final AssinaturaRepository assinaturaRepository;

    @Autowired
    private final AssinaturaMapper assinaturaMapper;

    public AssinaturaResponse save(AssinaturaRequest dto) {
        if (possuiAssinaturaAtiva(Long.valueOf(dto.userId()))) {
            Assinatura assinatura = assinaturaMapper.toEntity(dto);
            return assinaturaMapper.toResponseDTO(assinaturaRepository.save(assinatura));
        } else {
            new RuntimeException("O usuário possui uma assinatura válida");
            return null;
        }
    }

    public List<AssinaturaResponse> getAll() {
        return assinaturaRepository.findAll()
                .stream()
                .map(assinaturaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public AssinaturaResponse getById(Long id) {
        Assinatura assinatura = assinaturaRepository.findById(id).orElseThrow(() -> new RuntimeException("Assinatura não encontrada"));
        return assinaturaMapper.toResponseDTO(assinatura);
    }

    public AssinaturaResponse update(Long id, AssinaturaRequest dto) {
        Assinatura assinatura = assinaturaRepository.findById(id).orElseThrow(() -> new RuntimeException("Assinatura não encontrada"));
        TipoAssinatura tipoAssinatura = new TipoAssinatura();
        tipoAssinatura.setId(dto.tipoAssinatura().getId());

        assinatura.setCpf(dto.cpf());
        assinatura.setDataCompra(dto.dataCompra());
        assinatura.setPagamento(Pagamento.valueOf(dto.pagamento().toLowerCase()));
        assinatura.setTipoAssinatura(tipoAssinatura);
        return assinaturaMapper.toResponseDTO(assinaturaRepository.save(assinatura));
    }

    public void delete(Long id) {
        assinaturaRepository.deleteById(id);
    }

    public boolean isAssinaturaValida(Long id) {
        Assinatura assinatura = assinaturaRepository.findById(id).orElseThrow(() -> new RuntimeException("Assinatura não encontrada"));
        LocalDateTime dataCompra = assinatura.getDataCompra();
        LocalDateTime dataLimite = dataCompra.plusDays(30);
        LocalDateTime dataAtual = LocalDateTime.now();
        return dataAtual.isBefore(dataLimite);
    }

    public boolean possuiAssinaturaAtiva(Long usuarioId) {
        List<Assinatura> assinaturas = assinaturaRepository.findAllByUsuarioId(usuarioId);
        LocalDateTime agora = LocalDateTime.now();
        for (Assinatura assinatura : assinaturas) {
            LocalDateTime dataLimite = assinatura.getDataCompra().plusDays(30);
            if (agora.isBefore(dataLimite)) {
                return true;
            }
        }
        return false;
    }

}