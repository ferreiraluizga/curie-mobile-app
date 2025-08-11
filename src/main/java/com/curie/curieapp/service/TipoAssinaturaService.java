package com.curie.curieapp.service;


import com.curie.curieapp.dto.request.TipoAssinaturaRequest;
import com.curie.curieapp.dto.response.TipoAssinaturaResponse;
import com.curie.curieapp.entities.TipoAssinatura;
import com.curie.curieapp.mapper.TipoAssinaturaMapper;
import com.curie.curieapp.repository.TipoAssinaturaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TipoAssinaturaService {
    @Autowired
    private final TipoAssinaturaRepository tipoAssinaturaRepository;

    @Autowired
    private final TipoAssinaturaMapper tipoAssinaturaMapper;

    public TipoAssinaturaResponse save(TipoAssinaturaRequest dto) {
        TipoAssinatura tipoAssinatura = tipoAssinaturaMapper.toEntity(dto);
        return tipoAssinaturaMapper.toResponseDTO(tipoAssinaturaRepository.save(tipoAssinatura));
    }

    public List<TipoAssinaturaResponse> getAll() {
        return tipoAssinaturaRepository.findAll()
                .stream()
                .map(tipoAssinaturaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public TipoAssinaturaResponse getById(Long id) {
        TipoAssinatura tipoAssinatura = tipoAssinaturaRepository.findById(id).orElseThrow(() -> new RuntimeException("Tipo de assinatura não encontrado"));
        return tipoAssinaturaMapper.toResponseDTO(tipoAssinatura);
    }

    public TipoAssinaturaResponse update(Long id, TipoAssinaturaRequest dto) {
        TipoAssinatura tipoAssinatura = tipoAssinaturaRepository.findById(id).orElseThrow(() -> new RuntimeException("Tipo de assinatura não encontrado"));
        tipoAssinatura.setNome(dto.nome());
        tipoAssinatura.setDescricao(dto.descricao());
        tipoAssinatura.setValor(dto.valor());
        return tipoAssinaturaMapper.toResponseDTO(tipoAssinaturaRepository.save(tipoAssinatura));
    }

    public void delete(Long id) {
        tipoAssinaturaRepository.deleteById(id);
    }
}
