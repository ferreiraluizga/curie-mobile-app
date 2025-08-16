package com.curie.curieapp.service;


import com.curie.curieapp.dto.request.MensagensRequest;
import com.curie.curieapp.dto.response.MensagensResponse;
import com.curie.curieapp.entities.Mensagens;
import com.curie.curieapp.mapper.MensagensMapper;
import com.curie.curieapp.repository.MensagensRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MensagensService {
    @Autowired
    private final MensagensRepository mensagensRepository;

    @Autowired
    private final MensagensMapper mensagensMapper;

    public MensagensResponse save(MensagensRequest dto) {
        Mensagens mensagens = mensagensMapper.toEntity(dto);
        return mensagensMapper.toResponseDTO(mensagensRepository.save(mensagens));
    }

    public List<MensagensResponse> getAll() {
        return mensagensRepository.findAll()
                .stream()
                .map(mensagensMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public MensagensResponse getById(Long id) {
        Mensagens mensagens = mensagensRepository.findById(id).orElseThrow(() -> new RuntimeException("Mensagem não encontrada"));
        return mensagensMapper.toResponseDTO(mensagens);
    }

    public MensagensResponse update(Long id, MensagensRequest dto) {
        Mensagens mensagens = mensagensRepository.findById(id).orElseThrow(() -> new RuntimeException("Mensagem não encontrada"));
        mensagens.setData(dto.data());
        mensagens.setTexto(dto.texto());
        mensagens.setArquivo(dto.arquivo());
        return mensagensMapper.toResponseDTO(mensagensRepository.save(mensagens));
    }

    public void delete(Long id) {
        mensagensRepository.deleteById(id);
    }
}
