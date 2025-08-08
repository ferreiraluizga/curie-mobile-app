package com.curie.curieapp.service;


import com.curie.curieapp.dto.request.Tipo_AssinaturaRequest;
import com.curie.curieapp.dto.response.Tipo_AssinaturaResponse;
import com.curie.curieapp.entities.Tipo_Assinatura;
import com.curie.curieapp.mapper.Tipo_AssinaturaMapper;
import com.curie.curieapp.repository.Tipo_AssinaturaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class Tipo_AssinaturaService {
    @Autowired
    private final Tipo_AssinaturaRepository tipo_assinaturaRepository;

    @Autowired
    private final Tipo_AssinaturaMapper tipo_assinaturaMapper;

    public Tipo_AssinaturaResponse save(Tipo_AssinaturaRequest dto) {
        Tipo_Assinatura tipo_assinatura = tipo_assinaturaMapper.toEntity(dto);
        return tipo_assinaturaMapper.toResponseDTO(tipo_assinaturaRepository.save(tipo_assinatura));
    }

    public List<Tipo_AssinaturaResponse> getAll() {
        return tipo_assinaturaRepository.findAll()
                .stream()
                .map(tipo_assinaturaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Tipo_AssinaturaResponse getById(Long id) {
        Tipo_Assinatura tipo_assinatura = tipo_assinaturaRepository.findById(id).orElseThrow(() -> new RuntimeException("Tipo de assinatura não encontrada"));
        return tipo_assinaturaMapper.toResponseDTO(tipo_assinatura);
    }

    public Tipo_AssinaturaResponse update(Long id, Tipo_AssinaturaRequest dto) {
        Tipo_Assinatura tipo_assinatura = tipo_assinaturaRepository.findById(id).orElseThrow(() -> new RuntimeException("Tipo de assinatura não encontrada"));
        tipo_assinatura.setTipo(dto.tipo());
        tipo_assinatura.setDesc(dto.desc());
        tipo_assinatura.setValor(dto.valor());
        return tipo_assinaturaMapper.toResponseDTO(tipo_assinaturaRepository.save(tipo_assinatura));
    }

    public void delete(Long id) {
        tipo_assinaturaRepository.deleteById(id);
    }
}
