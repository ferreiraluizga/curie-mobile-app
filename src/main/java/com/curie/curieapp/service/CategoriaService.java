package com.curie.curieapp.service;

import com.curie.curieapp.dto.request.CategoriaRequest;
import com.curie.curieapp.dto.request.TipoAssinaturaRequest;
import com.curie.curieapp.dto.response.CategoriaResponse;
import com.curie.curieapp.dto.response.TipoAssinaturaResponse;
import com.curie.curieapp.entities.Categoria;
import com.curie.curieapp.entities.TipoAssinatura;
import com.curie.curieapp.mapper.CategoriaMapper;
import com.curie.curieapp.mapper.TipoAssinaturaMapper;
import com.curie.curieapp.repository.CategoriaRepository;
import com.curie.curieapp.repository.TipoAssinaturaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class CategoriaService {
    @Autowired
    private final CategoriaRepository categoriaRepository;

    @Autowired
    private final CategoriaMapper categoriaMapper;

    public CategoriaResponse save(CategoriaRequest dto) {
        Categoria categoria = categoriaMapper.toEntity(dto);
        return categoriaMapper.toResponseDTO(categoriaRepository.save(categoria));
    }

    public List<CategoriaResponse> getAll() {
        return categoriaRepository.findAll()
                .stream()
                .map(categoriaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public CategoriaResponse getById(Long id) {
        Categoria categoria = categoriaRepository.findById(id).orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        return categoriaMapper.toResponseDTO(categoria);
    }

    public CategoriaResponse update(Long id, CategoriaRequest dto) {
        Categoria categoria = categoriaRepository.findById(id).orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        categoria.setNome(dto.nome());
        categoria.setDescricao(dto.descricao());
        return categoriaMapper.toResponseDTO(categoriaRepository.save(categoria));
    }

    public void delete(Long id) {
        categoriaRepository.deleteById(id);
    }

}
