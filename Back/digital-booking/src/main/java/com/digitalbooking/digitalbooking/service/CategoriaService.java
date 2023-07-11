package com.digitalbooking.digitalbooking.service;

import com.digitalbooking.digitalbooking.domain.Categoria;
import com.digitalbooking.digitalbooking.dto.CategoriaDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CategoriaService {
   List<CategoriaDto> findAllCategorias();
   ResponseEntity<Optional<CategoriaDto>> findCategoriaByUid(Long uid);
   ResponseEntity<CategoriaDto> saveCategoria(CategoriaDto categoriaDto);
   ResponseEntity<CategoriaDto> updateCategoria(CategoriaDto categoriaDto);
   ResponseEntity<String> deleteCategoriaByUid(Long uid);
   Categoria findIdCategoriaByUid(Long uid);
}
