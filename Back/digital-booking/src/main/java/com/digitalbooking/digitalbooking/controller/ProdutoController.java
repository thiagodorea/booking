package com.digitalbooking.digitalbooking.controller;

import com.digitalbooking.digitalbooking.dto.ClienteDto;
import com.digitalbooking.digitalbooking.dto.ProdutoDto;
import com.digitalbooking.digitalbooking.dto.ProdutoNovoDto;
import com.digitalbooking.digitalbooking.service.ProdutoService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("produtos")
public class ProdutoController {

   @Autowired
   private ProdutoService produtoService;

   @Operation(summary = "Listar Produtos", description = "Retorna uma lista de Produtos", tags = "Produtos")
   @ApiResponses(value = {
            @ApiResponse(
               responseCode = "200", description = "OK",
               content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoDto.class))})   })
   @GetMapping
   public ResponseEntity<List<ProdutoDto>> findAllProdutos(){
      return produtoService.findAllProdutos();
   }


   @Operation(summary = "Listar Produtos por reserva do Usuario ", description = "Retorna uma lista de Produtos por reservas do usuario", tags = "Produtos")
   @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "OK",
                     content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoDto.class))}) })
   @GetMapping("{num}/reserva/usuario")
   public ResponseEntity<List<ProdutoDto>> findProdutosByReservasUsuarioEmail(@PathVariable Long num){
      return produtoService.findProdutoByReservaUsuarioNum(num);
   }

   @Operation(summary = "Listar Produtos por Cidade e/ou categoria e/ou por data ", description = "Retorna uma lista de Produtos por Cidade e/ou categoria e/ou data", tags = "Produtos")
   @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "OK",
                           content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoDto.class))}) })
   @GetMapping("/filtro")
   public ResponseEntity<List<ProdutoDto>> findProdutoByCidadeOrCategoriaOrData(@RequestParam(required = false) Long cidade,
                                                                                 @RequestParam(required = false) LocalDate dataInicial,
                                                                                 @RequestParam(required = false) LocalDate dataFinal){
      return produtoService.findProdutoByCidadeOrCategoriaOrData( cidade,dataInicial,dataFinal);
   }

   @Operation(summary = "Listar Produtos por Categoria", description = "Retorna uma lista de Produtos por categoria", tags = "Produtos")
   @ApiResponses(value = {
            @ApiResponse(
               responseCode = "200", description = "OK",
               content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoDto.class))})   })
   @GetMapping("{uid}/categoria")
   public ResponseEntity<List<ProdutoDto>> findProdutoByCategoriaUid(@PathVariable Long uid){
      return produtoService.findProdutoByCategoriaUid(uid);
   }

   @Operation(summary = "Listar Produtos por Cidade", description = "Retorna uma lista de Produtos por Cidade", tags = "Produtos")
   @ApiResponses(value = {
            @ApiResponse(
               responseCode = "200", description = "OK",
               content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoDto.class))})   })
   @GetMapping("{uid}/cidade")
   public ResponseEntity<List<ProdutoDto>> findProdutoByCidadeUid(@PathVariable Long uid){
      return produtoService.findProdutoByCidadeUid(uid);
   }

   @Operation(summary = "Retornar um Produto", description = "Retorna um Produto pelo UID", tags = "Produtos")
   @Parameter(required = true, description = "Informe um UID de um Produto",name = "uid", example = "2305181031025")
   @ApiResponses(value = {
            @ApiResponse(
               responseCode = "200", description = "Retorna um Produto ou null",
               content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoDto.class))}),
            @ApiResponse(
               responseCode = "404", description = "Produto 'uid' não encontrato.",
               content = {@Content(mediaType = "application/json", schema = @Schema(hidden = true))})  })
   @GetMapping("{uid}")
   public ResponseEntity<Optional<ProdutoDto>> findProdutoByUid(@PathVariable Long uid){
      return produtoService.findProdutoByUid(uid);
   }

   @Operation(summary = "Criar um Produto", description = "Cria um Produto", tags = "Produtos")
   @ApiResponses(value = {
         @ApiResponse(
               responseCode = "201", description = "OK",
               content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoDto.class))}),
         @ApiResponse(
               responseCode = "400", description = "Não foi possível criar um produto",
               content = {@Content(mediaType = "application/json", schema = @Schema(hidden = true))})  })
   @PostMapping
   @ResponseBody
   public ResponseEntity<ProdutoNovoDto> saveProduto(@RequestBody ProdutoNovoDto produtoNovoDto){
      return produtoService.saveProduto(produtoNovoDto);
   }

   @Operation(summary = "Atualizar um Produto", description = "Atualiza um Produto", tags = "Produtos")
   @ApiResponses(value = {
            @ApiResponse(
               responseCode = "201", description = "Created",
               content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoDto.class))}),
            @ApiResponse(
               responseCode = "400", description = "Erro ao atualizar produto.",
               content = {@Content(mediaType = "application/json", schema = @Schema(hidden = true))})  })
   @PatchMapping
   @ResponseBody
   public ResponseEntity<ProdutoNovoDto> updateProduto(@RequestBody ProdutoNovoDto produtoNovoDto) {
      return produtoService.updateProduto(produtoNovoDto);
   }

   @Operation(summary = "Deletar um Produto", description = "Deleta um Produto pelo UID", tags = "Produtos")
   @Parameter(required = true, description = "Informe um UID de um Produto",name = "uid", example = "2305181031025")
   @ApiResponses(value = {
            @ApiResponse(
               responseCode = "200", description = "Produto 'Descricao' excluid com sucesso.",
               content = {@Content(mediaType = "application/json", schema = @Schema(hidden = true))}),
            @ApiResponse(
               responseCode = "404", description = "Erro ao excluir o produto",
               content = {@Content(mediaType = "application/json", schema = @Schema(hidden = true))})  })
   @DeleteMapping("{uid}")
   public ResponseEntity<String> deleteProduto(@PathVariable Long uid){
      return produtoService.deleteProdutoByUid(uid);
   }

}
