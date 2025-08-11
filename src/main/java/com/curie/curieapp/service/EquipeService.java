package com.curie.curieapp.service;

import com.curie.curieapp.dto.request.EquipeRequest;
import com.curie.curieapp.dto.response.EquipeResponse;
import com.curie.curieapp.entities.Equipe;
import com.curie.curieapp.mapper.EquipeMapper;
import com.curie.curieapp.repository.EquipeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EquipeService {

    @Autowired
    private final EquipeRepository equipeRepository;

    @Autowired
    private final EquipeMapper equipeMapper;

    @Autowired
    private final AssinaturaService assinaturaService;

    public EquipeResponse save(EquipeRequest dto) {
        if (assinaturaService.isAssinaturaValida(dto.assinatura().getId().longValue())) {
            Equipe equipe = equipeMapper.toEntity(dto);
            return equipeMapper.toResponseDTO(equipeRepository.save(equipe));
        } else {
            throw new RuntimeException("Assinatura inválida");
        }
    }

    public List<EquipeResponse> getAll() {
        return equipeRepository.findAll()
                .stream()
                .map(equipeMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public EquipeResponse getById(Long id) {
        Equipe equipe = equipeRepository.findById(id).orElseThrow(() -> new RuntimeException("Equipe não encontrada"));
        return equipeMapper.toResponseDTO(equipe);
    }

    public void delete(Long id) {
        equipeRepository.deleteById(id);
    }

    public boolean isEquipeCheia(Long equipeId) {
        int quantidadeMembros = equipeRepository.quantidadeMembros(equipeId);
        return quantidadeMembros >= 4;
    }
}
