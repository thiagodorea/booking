package com.digitalbooking.digitalbooking.controller;

import com.digitalbooking.digitalbooking.dto.CategoriaDto;
import com.digitalbooking.digitalbooking.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("categorias")
public class CategoriaController {

   @Autowired
   private CategoriaService categoriaService;

   @Operation(summary = "Listar Categorias", description = "Retorna uma lista de Categorias", tags = "Categorias")
   @ApiResponses(value = {
            @ApiResponse(
               responseCode = "200", description = "OK",
               content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CategoriaDto.class))})   })
   @GetMapping
   public List<CategoriaDto> findAllCategorias(){
      return categoriaService.findAllCategorias();
   }

   @Operation(summary = "Retornar uma Categoria", description = "Retorna uma Categorias pelo UID", tags = "Categorias")
   @Parameter(required = true, description = "Informe um UID de uma Categoria",name = "uid", example = "2305181031025")
   @ApiResponses(value = {
            @ApiResponse(
               responseCode = "200", description = "Retorna uma Categoria ou null",
               content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CategoriaDto.class))}),
            @ApiResponse(
               responseCode = "404", description = "Categoria 'uid' não encontrato.",
               content = {@Content(mediaType = "application/json", schema = @Schema(hidden = true))})  })
   @GetMapping("{uid}")
   public ResponseEntity<Optional<CategoriaDto>> findCategoriaByUid(@PathVariable Long uid){
      return categoriaService.findCategoriaByUid(uid);
   }

   @Operation(summary = "Criar uma Categoria", description = "Cria uma Categoria", tags = "Categorias")
   @ApiResponses(value = {
         @ApiResponse(
               responseCode = "201", description = "OK",
               content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CategoriaDto.class))}),
         @ApiResponse(
               responseCode = "400", description = "Não foi possível criar a categoria",
               content = {@Content(mediaType = "application/json", schema = @Schema(hidden = true))})  })
   @PostMapping
   @ResponseBody
   public ResponseEntity<CategoriaDto> saveCategoria(@RequestBody CategoriaDto categoriaDto){
      return categoriaService.saveCategoria(categoriaDto);
   }

   @Operation(summary = "Atualizar uma Categoria", description = "Atualiza uma Categoria", tags = "Categorias")
   @ApiResponses(value = {
            @ApiResponse(
               responseCode = "201", description = "Created",
               content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CategoriaDto.class))}),
            @ApiResponse(
               responseCode = "400", description = "Erro ao atualizar categoria.",
               content = {@Content(mediaType = "application/json", schema = @Schema(hidden = true))})  })
   @PatchMapping
   @ResponseBody
   public ResponseEntity<CategoriaDto> updateCategoria(@RequestBody CategoriaDto categoriaDto) {
      return categoriaService.updateCategoria(categoriaDto);
   }

   @Operation(summary = "Deletar uma Categoria", description = "Deleta uma Categorias pelo UID", tags = "Categorias")
   @Parameter(required = true, description = "Informe um UID de uma Categoria",name = "uid", example = "2305181031025")
   @ApiResponses(value = {
            @ApiResponse(
               responseCode = "200", description = "Categoria 'Descricao' excluida com sucesso.",
               content = {@Content(mediaType = "application/json", schema = @Schema(hidden = true))}),
            @ApiResponse(
               responseCode = "404", description = "Erro ao excluir a categoria",
               content = {@Content(mediaType = "application/json", schema = @Schema(hidden = true))})  })
   @DeleteMapping("{uid}")
   public ResponseEntity<String> deleteCategoria(@PathVariable Long uid){
      return categoriaService.deleteCategoriaByUid(uid);
   }

}
