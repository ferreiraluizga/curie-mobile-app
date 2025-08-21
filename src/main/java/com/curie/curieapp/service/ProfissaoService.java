package com.curie.curieapp.service;

import com.curie.curieapp.dto.request.ProfissaoRequest;
import com.curie.curieapp.dto.response.ProfissaoResponse;
import com.curie.curieapp.entities.Profissao;
import com.curie.curieapp.entities.enums.Demanda;
import com.curie.curieapp.mapper.ProfissaoMapper;
import com.curie.curieapp.repository.ProfissaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProfissaoService {

    @Autowired
    private final ProfissaoRepository profissaoRepository;

    @Autowired
    private final ProfissaoMapper profissaoMapper;

    public ProfissaoResponse save(ProfissaoRequest dto) {
        Profissao profissao = profissaoMapper.toEntity(dto);
        return profissaoMapper.toResponseDTO(profissaoRepository.save(profissao));
    }

    public List<ProfissaoResponse> getAll() {
        return profissaoRepository.findAll()
                .stream()
                .map(profissaoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ProfissaoResponse getById(Long id) {
        Profissao profissao = profissaoRepository.findById(id).orElseThrow(() -> new RuntimeException("Profissão não encontrada"));
        return profissaoMapper.toResponseDTO(profissao);
    }

    public ProfissaoResponse update(Long id, ProfissaoRequest dto) {
        Profissao profissao = profissaoRepository.findById(id).orElseThrow(() -> new RuntimeException("Profissão não encontrada"));
        profissao.setNome(dto.nome());
        profissao.setDescricao(dto.descricao());
        profissao.setSalario(dto.salario());
        profissao.setDemanda(Demanda.valueOf(dto.demanda().toLowerCase()));
        return profissaoMapper.toResponseDTO(profissaoRepository.save(profissao));
    }

    public void delete(Long id) {
        profissaoRepository.deleteById(id);
    }

}
