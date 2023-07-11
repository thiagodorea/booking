package com.digitalbooking.digitalbooking.service;

import com.digitalbooking.digitalbooking.dto.ClienteDto;
import com.digitalbooking.digitalbooking.dto.ReservaDto;
import com.digitalbooking.digitalbooking.dto.UsuarioDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ReservaService {

   ResponseEntity<List<ReservaDto>> findReservaByUsuarioEmail(ClienteDto clienteDto);
   ResponseEntity<Optional<ReservaDto>> findReservaByUid(Long uid);
   ResponseEntity<ReservaDto> saveReserva(ReservaDto categoriaDto);

}
