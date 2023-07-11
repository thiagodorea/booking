package com.digitalbooking.digitalbooking.service;

import com.digitalbooking.digitalbooking.domain.Perfil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PerfilService {
   List<Perfil> findAllPerfil();

}
