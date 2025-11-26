package com.curie.curieapp.service;

import com.curie.curieapp.dto.request.PerfilRequest;
import com.curie.curieapp.dto.response.PerfilResponse;
import com.curie.curieapp.entities.*;
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
        Long userId = dto.userId().longValue();
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

        // Atualiza descrição
        if (dto.descricao() != null)
            perfilExistente.setDescricao(dto.descricao());

        // Comportamento
        if (dto.comportamentoId() != null) {
            Comportamento comportamento = new Comportamento();
            comportamento.setId(Math.toIntExact(dto.comportamentoId()));
            perfilExistente.setComportamento(comportamento);
        }

        // Temperamento
        if (dto.temperamentoId() != null) {
            Temperamento temperamento = new Temperamento();
            temperamento.setId(Math.toIntExact(dto.temperamentoId()));
            perfilExistente.setTemperamento(temperamento);
        }

        // Força
        if (dto.forcaId() != null) {
            AreaConhecimento forca = new AreaConhecimento();
            forca.setId(Math.toIntExact(dto.forcaId()));
            perfilExistente.setForca(forca);
        }

        // Fraqueza
        if (dto.fraquezaId() != null) {
            AreaConhecimento fraqueza = new AreaConhecimento();
            fraqueza.setId(Math.toIntExact(dto.fraquezaId()));
            perfilExistente.setFraqueza(fraqueza);
        }

        // User
        if (dto.userId() != null) {
            User user = new User();
            user.setId(Math.toIntExact(dto.userId()));
            perfilExistente.setUser(user);
        }

        Perfil atualizado = perfilRepository.save(perfilExistente);
        return perfilMapper.toResponseDTO(atualizado);
    }


    public void delete(Long id) {
        perfilRepository.deleteById(id);
    }
}
