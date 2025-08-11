package com.curie.curieapp.service;

import com.curie.curieapp.dto.request.MembroEquipeRequest;
import com.curie.curieapp.dto.response.MembroEquipeResponse;
import com.curie.curieapp.entities.MembroEquipe;
import com.curie.curieapp.mapper.MembroEquipeMapper;
import com.curie.curieapp.repository.MembroEquipeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MembroEquipeService {

    @Autowired
    private final MembroEquipeRepository membroEquipeRepository;

    @Autowired
    private final MembroEquipeMapper membroEquipeMapper;

    @Autowired
    private final EquipeService equipeService;

    public MembroEquipeResponse save(MembroEquipeRequest dto) {
        if (equipeService.isEquipeCheia(dto.equipe().getId().longValue())) {
            MembroEquipe membroEquipe = membroEquipeMapper.toEntity(dto);
            return membroEquipeMapper.toResponseDTO(membroEquipeRepository.save(membroEquipe));
        } else {
            throw new RuntimeException("Equipe inválida");
        }
    }

    public List<MembroEquipeResponse> getAll(Long id) {
        return membroEquipeRepository.membrosEquipe(id)
                .stream()
                .map(membroEquipeMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        membroEquipeRepository.deleteById(id);
    }
}
