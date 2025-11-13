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
        Long userId = dto.user().getId().longValue();
        List<Perfil> perfisUsuario = perfilRepository.getByUsuario(userId);

        if (perfisUsuario.size() >= 3) {
            // Remove o mais antigo (mantém os 3 mais recentes)
            Perfil maisAntigo = perfisUsuario.get(0);
            perfilRepository.deleteById(maisAntigo.getId().longValue());
            comportamentoRepository.deleteByUsuario(userId);
            temperamentoRepository.deleteByUsuario(userId);
        }

        Perfil perfil = perfilMapper.toEntity(dto);
        Perfil salvo = perfilRepository.save(perfil);
        return perfilMapper.toResponseDTO(salvo);
    }

    public List<PerfilResponse> getByUsuario(Long id) {
        return perfilRepository.getByUsuario(id)
                .stream()
                .map(perfilMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public PerfilResponse getById(Long id) {
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));
        return perfilMapper.toResponseDTO(perfil);
    }

    public PerfilResponse getMaisRecente(Long id) {
        Perfil perfil = perfilRepository.getMaisRecente(id);
        if (perfil == null) {
            throw new RuntimeException("Nenhum perfil encontrado para o usuário ID: " + id);
        }
        return perfilMapper.toResponseDTO(perfil);
    }

    public PerfilResponse update(Long id, PerfilRequest dto) {
        Perfil perfilExistente = perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado para atualização: " + id));

        // Atualiza campos relevantes somente se não forem nulos
        if (dto.descricao() != null)
            perfilExistente.setDescricao(dto.descricao());

        if (dto.comportamento() != null)
            perfilExistente.setComportamento(dto.comportamento());

        if (dto.temperamento() != null)
            perfilExistente.setTemperamento(dto.temperamento());

        if (dto.forca() != null)
            perfilExistente.setForca(dto.forca());

        if (dto.fraqueza() != null)
            perfilExistente.setFraqueza(dto.fraqueza());

        if (dto.user() != null)
            perfilExistente.setUser(dto.user());

        Perfil atualizado = perfilRepository.save(perfilExistente);
        return perfilMapper.toResponseDTO(atualizado);
    }

    public void delete(Long id) {
        perfilRepository.deleteById(id);
    }
}
