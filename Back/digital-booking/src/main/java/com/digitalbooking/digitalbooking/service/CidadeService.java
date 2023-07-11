package com.digitalbooking.digitalbooking.service;

import com.digitalbooking.digitalbooking.domain.Categoria;
import com.digitalbooking.digitalbooking.domain.Cidade;
import com.digitalbooking.digitalbooking.dto.CidadeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CidadeService {
   ResponseEntity<List<CidadeDto>> findAllCidades();
   ResponseEntity<CidadeDto> saveCidade(CidadeDto cidadeDto);
   Cidade findIdCidadeByUid(Long uid);

}
