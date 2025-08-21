package com.curie.curieapp.service;

import com.curie.curieapp.dto.request.PosGraduacaoRequest;
import com.curie.curieapp.dto.response.PosGraduacaoResponse;
import com.curie.curieapp.entities.PosGraduacao;
import com.curie.curieapp.mapper.PosGraduacaoMapper;
import com.curie.curieapp.repository.PosGraduacaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PosGraduacaoService {

    @Autowired
    private final PosGraduacaoRepository posGraduacaoRepository;

    @Autowired
    private final PosGraduacaoMapper posGraduacaoMapper;

    public PosGraduacaoResponse save(PosGraduacaoRequest dto) {
        PosGraduacao posGraduacao = posGraduacaoMapper.toEntity(dto);
        return posGraduacaoMapper.toResponseDTO(posGraduacaoRepository.save(posGraduacao));
    }

    public List<PosGraduacaoResponse> getAll() {
        return posGraduacaoRepository.findAll()
                .stream()
                .map(posGraduacaoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public PosGraduacaoResponse getById(Long id) {
        PosGraduacao posGraduacao = posGraduacaoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pos Graduacao não encontrada"));
        return posGraduacaoMapper.toResponseDTO(posGraduacao);
    }

    public PosGraduacaoResponse update(Long id, PosGraduacaoRequest dto) {
        PosGraduacao posGraduacao = posGraduacaoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pos Graduacao não encontrada"));
        posGraduacao.setNome(dto.nome());
        posGraduacao.setDescricao(dto.descricao());
        posGraduacao.setDuracao(dto.duracao());
        posGraduacao.setAreaCarreira(dto.areaCarreira());
        return posGraduacaoMapper.toResponseDTO(posGraduacaoRepository.save(posGraduacao));
    }

    public void delete(Long id) {
        posGraduacaoRepository.deleteById(id);
    }

}
