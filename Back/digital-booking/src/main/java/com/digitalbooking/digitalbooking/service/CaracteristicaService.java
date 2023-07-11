package com.digitalbooking.digitalbooking.service;

import com.digitalbooking.digitalbooking.domain.Caracteristica;
import com.digitalbooking.digitalbooking.dto.CaracteristicaDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CaracteristicaService {
   List<Caracteristica> findAllCaracteristica();
   List<CaracteristicaDto> findAllCaracteristicaDTO();


}
