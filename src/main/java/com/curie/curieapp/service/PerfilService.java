package com.curie.curieapp.service;


import com.curie.curieapp.dto.request.PerfilRequest;
import com.curie.curieapp.dto.response.PerfilResponse;
import com.curie.curieapp.entities.Perfil;
import com.curie.curieapp.mapper.PerfilMapper;
import com.curie.curieapp.repository.ComportamentoRepository;
import com.curie.curieapp.repository.PerfilRepository;
import com.curie.curieapp.repository.TemperamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PerfilService {
    @Autowired
    private final PerfilRepository perfilRepository;

    @Autowired
    private final ComportamentoRepository comportamentoRepository;

    @Autowired
    private final TemperamentoRepository temperamentoRepository;

    @Autowired
    private final PerfilMapper perfilMapper;

    public PerfilResponse save(PerfilRequest dto) {
        if (isMaximoPerfis(dto.user().getId().longValue())) {
            perfilRepository.deleteByUsuario(dto.user().getId().longValue());
            comportamentoRepository.deleteByUsuario(dto.user().getId().longValue());
            temperamentoRepository.deleteByUsuario(dto.user().getId().longValue());
        }
        Perfil perfil = perfilMapper.toEntity(dto);
        return perfilMapper.toResponseDTO(perfilRepository.save(perfil));
    }

    public List<PerfilResponse> getByUsuario(Long id) {
        return perfilRepository.getByUsuario(id)
                .stream()
                .map(perfilMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public PerfilResponse getById(Long id) {
        Perfil perfil = perfilRepository.findById(id).orElseThrow(() -> new RuntimeException("Perfil não encontrado"));
        return perfilMapper.toResponseDTO(perfil);
    }

    public PerfilResponse getMaisRecente(Long id) {
        Perfil perfil = perfilRepository.getMaisRecente(id);
        return perfilMapper.toResponseDTO(perfil);
    }

    public void delete(Long id) {
        perfilRepository.deleteById(id);
    }

    private boolean isMaximoPerfis(Long userId) {
        int quant = perfilRepository.getByUsuario(userId).size();
        if (quant > 3) {
            return true;
        } else {
            return false;
        }
    }
}