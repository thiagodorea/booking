package com.digitalbooking.digitalbooking.service.impl;

import com.digitalbooking.digitalbooking.domain.Cidade;
import com.digitalbooking.digitalbooking.dto.CidadeDto;
import com.digitalbooking.digitalbooking.repository.CidadeRepository;
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
public class CidadeServiceImpl implements CidadeService {

   @Autowired
   private CidadeRepository cidadeRepository;

   @Override
   @Transactional(readOnly = true)
   public ResponseEntity<List<CidadeDto>> findAllCidades() {
      return ResponseEntity.status(HttpStatus.OK).body(cidadeRepository.findAll().stream().map(CidadeDto::new).collect(Collectors.toList()))  ;
   }

   @Override
   public ResponseEntity<CidadeDto> saveCidade(CidadeDto cidadeDto) {
      try {
         cidadeDto.setUid(new Utils().gerarUid());
         Cidade cidade = new Cidade(cidadeDto);
         return ResponseEntity.status(HttpStatus.CREATED).body(new CidadeDto(cidadeRepository.save(cidade)));
      }catch (ConstraintViolationException e){
         log.error("[CidadeService] [saveCidade] Não foi possível salvar a cidade: " + cidadeDto.toString());
         List<String> msgViolacao = new ArrayList<>();
         e.getConstraintViolations().stream().map(violation -> msgViolacao.add( violation.getMessage() )).collect(Collectors.toList());
         return new ResponseEntity("Dados obrigatórios faltando: " +msgViolacao , HttpStatus.BAD_REQUEST);
      }catch (Exception e) {
         log.error("[CidadeService] [saveCidade] Não foi possível salvar a cidade: " + e);
         return new ResponseEntity("Não foi possível salvar a cidade: " + cidadeDto.getNome(), HttpStatus.BAD_REQUEST);
      }
   }

   @Override
   public Cidade findIdCidadeByUid(Long uid) {
      return cidadeRepository.findByUid(uid).orElseThrow(() -> new NotFoundException("UID cidade não localizado"));
   }
}
