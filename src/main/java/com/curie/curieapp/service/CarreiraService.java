package com.curie.curieapp.service;

import com.curie.curieapp.dto.request.CarreiraRequest;
import com.curie.curieapp.dto.response.CarreiraResponse;
import com.curie.curieapp.entities.Carreira;
import com.curie.curieapp.mapper.CarreiraMapper;
import com.curie.curieapp.repository.CarreiraRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CarreiraService {

    @Autowired
    private final CarreiraRepository carreiraRepository;

    @Autowired
    private final CarreiraMapper carreiraMapper;

    public CarreiraResponse save(CarreiraRequest dto) {
        Carreira carreira = carreiraMapper.toEntity(dto);
        return carreiraMapper.toResponseDTO(carreiraRepository.save(carreira));
    }

    public List<CarreiraResponse> getAll() {
        return carreiraRepository.findAll()
                .stream()
                .map(carreiraMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public CarreiraResponse getById(Long id) {
        Carreira areaCarreira = carreiraRepository.findById(id).orElseThrow(() -> new RuntimeException("Carreira não encontrada"));
        return carreiraMapper.toResponseDTO(areaCarreira);
    }

    public CarreiraResponse update(Long id, CarreiraRequest dto) {
        Carreira carreira = carreiraRepository.findById(id).orElseThrow(() -> new RuntimeException("Carreira não encontrada"));
        carreira.setUser(dto.user());
        carreira.setDescricao(dto.descricao());
        carreira.setProfissao(dto.profissao());
        carreira.setGraduacao(dto.graduacao());
        carreira.setPosGraduacao(dto.posGraduacao());
        return carreiraMapper.toResponseDTO(carreiraRepository.save(carreira));
    }

    public void delete(Long id) {
        carreiraRepository.deleteById(id);
    }

}
