package com.curie.curieapp.service;


import com.curie.curieapp.dto.request.AssinaturaRequest;
import com.curie.curieapp.dto.response.AssinaturaResponse;
import com.curie.curieapp.entities.Assinatura;
import com.curie.curieapp.entities.Tipo_Assinatura;
import com.curie.curieapp.mapper.AssinaturaMapper;
import com.curie.curieapp.repository.AssinaturaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Assinatura assinatura = assinaturaMapper.toEntity(dto);
        return assinaturaMapper.toResponseDTO(assinaturaRepository.save(assinatura));
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

    public  AssinaturaResponse update(Long id, AssinaturaRequest dto) {
        Assinatura assinatura = assinaturaRepository.findById(id).orElseThrow(() -> new RuntimeException("Assinatura não encontrada"));
        Tipo_Assinatura tipo_assinatura = new Tipo_Assinatura();
        tipo_assinatura.setId(dto.tipoId());

        assinatura.setCpf(dto.cpf());
        assinatura.setData_compra(dto.data_compra());
        assinatura.setPagamento(Assinatura.Pagamento.valueOf(dto.pagamento().toLowerCase()));
        assinatura.setTipo_assinatura(tipo_assinatura);
        return assinaturaMapper.toResponseDTO(assinaturaRepository.save(assinatura));
    }

    public void delete(Long id) {
        assinaturaRepository.deleteById(id);
    }

}