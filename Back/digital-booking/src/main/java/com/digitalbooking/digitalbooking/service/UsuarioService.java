package com.digitalbooking.digitalbooking.service;

import com.digitalbooking.digitalbooking.dto.UsuarioNovoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UsuarioService {
   ResponseEntity<UsuarioNovoDto> saveUsuario(UsuarioNovoDto usuarioNovoDto);


}
