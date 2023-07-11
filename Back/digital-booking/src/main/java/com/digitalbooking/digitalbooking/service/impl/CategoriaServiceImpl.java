package com.digitalbooking.digitalbooking.service.impl;

import com.digitalbooking.digitalbooking.domain.Categoria;
import com.digitalbooking.digitalbooking.dto.CategoriaDto;
import com.digitalbooking.digitalbooking.repository.CategoriaRepository;
import com.digitalbooking.digitalbooking.service.CategoriaService;
import com.digitalbooking.digitalbooking.utils.Utils;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class CategoriaServiceImpl implements CategoriaService {

   @Autowired
   private CategoriaRepository categoriaRepository;


   @Override
   @Transactional(readOnly = true)
   public List<CategoriaDto> findAllCategorias() {
      List<Categoria> categoriaLista = categoriaRepository.findAll();
      return categoriaLista.stream().map(categoria -> new CategoriaDto(categoria)).toList();
   }

   @Override
   @Transactional(readOnly = true)
   public ResponseEntity<Optional<CategoriaDto>> findCategoriaByUid(Long uid) {
      try{
         Optional<CategoriaDto> categoriaDto  =  categoriaRepository.findByUid(uid).map(categoria -> new CategoriaDto(categoria));
         categoriaDto.orElseThrow(() -> new ObjectNotFoundException(categoriaDto, CategoriaDto.class.getName()));
         return ResponseEntity.status(HttpStatus.OK).body( categoriaDto);
      }catch(Exception e){
         return new ResponseEntity("Categoria " +uid+" não encontrato.",HttpStatus.NOT_FOUND);
      }
   }

   @Override
   public ResponseEntity<CategoriaDto> saveCategoria(CategoriaDto categoriaDto) {
      try{
         categoriaDto.setUid(new Utils().gerarUid());
         Categoria categoria = new Categoria(categoriaDto);
         return ResponseEntity.status(HttpStatus.CREATED).body(new CategoriaDto(categoriaRepository.save(categoria)));
      }catch (ConstraintViolationException e){
         log.error("[CategoriaService] [saveCategoria] Dados obrigatórios faltando: " + categoriaDto.toString());
         List<String> msgViolacao = new ArrayList<>();
         e.getConstraintViolations().stream().map(violation -> msgViolacao.add( violation.getMessage() )).collect(Collectors.toList());
         return new ResponseEntity("Dados obrigatórios faltando: " +msgViolacao , HttpStatus.BAD_REQUEST);
      }
   }

   @Override
   public ResponseEntity<CategoriaDto> updateCategoria(CategoriaDto categoriaDto){
      try {
         Categoria categoria = findIdCategoriaByUid(categoriaDto.getUid());
         categoria.setDescricao(categoriaDto.getDescricao());
         categoria.setUrlImagem(categoriaDto.getUrlImagem());
         return ResponseEntity.status(HttpStatus.OK).body(new CategoriaDto(categoriaRepository.save(categoria)));
      }catch (ConstraintViolationException e){
         log.error("[CategoriaService] [saveCategoria] Dados obrigatórios faltando: " + categoriaDto.toString());
         List<String> msgViolacao = new ArrayList<>();
         e.getConstraintViolations().stream().map(violation -> msgViolacao.add( violation.getMessage() )).collect(Collectors.toList());
         return new ResponseEntity("Dados obrigatórios faltando: " +msgViolacao , HttpStatus.BAD_REQUEST);
      } catch (NotFoundException e) {
         log.error("[ProdutoService] [deleteProdutoByUid] ", e.getMessage());
         return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
      } catch (Exception e){
         log.error("[CategoriaService] [saveCategoria] Dados obrigatórios faltando: " + categoriaDto.toString());
         return new ResponseEntity("Não foi possivel atualizar os dados. Dados obrigatórios faltando. ", HttpStatus.BAD_REQUEST);
      }
   }

   @Override
   public ResponseEntity<String> deleteCategoriaByUid(Long uid) {
      try {
         Categoria categoria = findIdCategoriaByUid(uid);
         categoriaRepository.deleteById(categoria.getId());
         return ResponseEntity.status(HttpStatus.OK).body("Categoria " +categoria.getDescricao() + " excluida com sucesso.");
      } catch (NotFoundException e) {
         log.error("[CategoriaService] [deleteCategoriaByUid] ", e.getMessage());
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
      }catch (Exception e) {
         log.error("[CategoriaService] [deleteCategoriaByUid] Erro ao excluir Categoria", e);
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao excluir a categoria" );
      }
   }

   @Override
   public Categoria findIdCategoriaByUid(Long uid) {
      return categoriaRepository.findByUid(uid).orElseThrow(() -> new NotFoundException("UID categoria não localizado"));
   }

}
