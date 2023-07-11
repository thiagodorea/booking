package com.digitalbooking.digitalbooking.service.impl;

import com.digitalbooking.digitalbooking.domain.Caracteristica;
import com.digitalbooking.digitalbooking.domain.Perfil;
import com.digitalbooking.digitalbooking.dto.CaracteristicaDto;
import com.digitalbooking.digitalbooking.dto.PerfilDto;
import com.digitalbooking.digitalbooking.repository.CaracteristicaRepository;
import com.digitalbooking.digitalbooking.repository.PerfilRepository;
import com.digitalbooking.digitalbooking.service.CaracteristicaService;
import com.digitalbooking.digitalbooking.service.PerfilService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class PerfilServiceImpl implements PerfilService {

   @Autowired
   private PerfilRepository perfilRepository;

   @Override
   public List<Perfil> findAllPerfil() {
      return perfilRepository.findAll().stream().collect(Collectors.toList());
   }

   private PerfilDto toMapPerfilDto(Perfil perfil) {
      return PerfilDto.builder()
               .nome(perfil.getNome())
               .build();
   }

}
