package com.digitalbooking.digitalbooking.service.impl;

import com.digitalbooking.digitalbooking.domain.Caracteristica;
import com.digitalbooking.digitalbooking.domain.Cidade;
import com.digitalbooking.digitalbooking.domain.Produto;
import com.digitalbooking.digitalbooking.dto.CaracteristicaDto;
import com.digitalbooking.digitalbooking.dto.CategoriaDto;
import com.digitalbooking.digitalbooking.dto.CidadeDto;
import com.digitalbooking.digitalbooking.dto.ImagemDto;
import com.digitalbooking.digitalbooking.dto.ProdutoDto;
import com.digitalbooking.digitalbooking.repository.CaracteristicaRepository;
import com.digitalbooking.digitalbooking.repository.CidadeRepository;
import com.digitalbooking.digitalbooking.service.CaracteristicaService;
import com.digitalbooking.digitalbooking.service.CidadeService;
import com.digitalbooking.digitalbooking.utils.Utils;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class CaracteristicaServiceImpl implements CaracteristicaService {

   @Autowired
   private CaracteristicaRepository caracteristicaRepository;

   @Override
   public List<Caracteristica> findAllCaracteristica() {
      return caracteristicaRepository.findAll().stream().collect(Collectors.toList());
   }

   @Override
   public List<CaracteristicaDto> findAllCaracteristicaDTO() {
      return caracteristicaRepository.findAll().stream().map(caracteristica -> toMapCaracteristicaDto(caracteristica)).collect(Collectors.toList());
   }

   private CaracteristicaDto toMapCaracteristicaDto(Caracteristica caracteristica) {
      return CaracteristicaDto.builder()
               .uid(caracteristica.getUid())
               .nome(caracteristica.getNome())
               .icone(caracteristica.getIcone()).build();
   }
}
