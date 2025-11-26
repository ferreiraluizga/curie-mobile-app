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
        if (isMaximoCarreiras(dto.userId().longValue())) {
            carreiraRepository.deleteByUsuario(dto.userId().longValue());
        }
        Carreira carreira = carreiraMapper.toEntity(dto);
        return carreiraMapper.toResponseDTO(carreiraRepository.save(carreira));
    }

    public List<CarreiraResponse> getByUsuario(Long id) {
        return carreiraRepository.getByUsuario(id)
                .stream()
                .map(carreiraMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public CarreiraResponse getMaisRecente(Long id) {
        Carreira carreira = carreiraRepository.getMaisRecente(id);
        return carreiraMapper.toResponseDTO(carreira);
    }

    public CarreiraResponse getById(Long id) {
        Carreira carreira = carreiraRepository.findById(id).orElseThrow(() -> new RuntimeException("Carreira não encontrada"));
        return carreiraMapper.toResponseDTO(carreira);
    }

    public void delete(Long id) {
        carreiraRepository.deleteById(id);
    }

    private boolean isMaximoCarreiras(Long userId) {
        int quant = carreiraRepository.getByUsuario(userId).size();
        if (quant > 3) {
            return true;
        } else {
            return false;
        }
    }

}
