package com.digitalbooking.digitalbooking.service.impl;

import com.digitalbooking.digitalbooking.domain.Categoria;
import com.digitalbooking.digitalbooking.dto.CategoriaDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Verifica a classe de serviço de categoria")
class CategoriaServiceImplTest {

   @Autowired
   private CategoriaServiceImpl service;
   @BeforeEach
   void init() {
      if(service.findAllCategorias().size() == 0) {
         CategoriaDto catDto = CategoriaDto.builder()
               .uid(4L)
               .descricao("Hotel")
               .urlImagem("https://unsplash.com/pt-br/fotografias/hDbCjHNdF48")
               .build();
         service.saveCategoria(catDto);
      }


   }

   @Test
   @DisplayName("Busca todas as categorias e verifica se tem retorno")
   void findAllCategorias() {
      List<CategoriaDto> categoriaLista = service.findAllCategorias();
      assertTrue(categoriaLista.size() > 0);
   }

   @Test
   @DisplayName("Busca uma categoria por UID")
   void findCategoriaByUid() {
      Long UID = service.findAllCategorias().get(0).getUid();
      ResponseEntity cat = service.findCategoriaByUid(UID);
      assertTrue(cat.getStatusCode().is2xxSuccessful());
   }

   @Test
   @DisplayName("Salva uma nova Categoria")
   void saveCategoria() {
      CategoriaDto newCatDto = CategoriaDto.builder()
                     .uid(3L)
                     .descricao("Hotel")
                     .urlImagem("https://unsplash.com").build();
      ResponseEntity catDto = service.saveCategoria(newCatDto);
      assertTrue(catDto.getBody().toString().contains("https://unsplash.com"));
   }

   @Test
   @DisplayName("Atualiza uma Categoria")
   void updateCategoria() {
      CategoriaDto catOld = service.findAllCategorias().get(0);
      CategoriaDto newCategoria =  CategoriaDto.builder()
            .uid(2L)
            .descricao("Pousada")
            .urlImagem("https://unsplash.com").build();
      ResponseEntity newCat = service.updateCategoria(newCategoria);
      assertNotEquals(catOld,newCat);
   }

   @Test
   @DisplayName("Deleta uma categoria")
   void deleteCategoriaByUid() {
      CategoriaDto catDto = service.findAllCategorias().get(0);
      ResponseEntity cat = service.deleteCategoriaByUid(catDto.getUid());
      assertEquals(cat.getBody(),"Categoria " +catDto.getDescricao() + " excluida com sucesso.");
   }
}