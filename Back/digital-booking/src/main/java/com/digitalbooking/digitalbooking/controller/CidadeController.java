package com.digitalbooking.digitalbooking.controller;

import com.digitalbooking.digitalbooking.dto.CategoriaDto;
import com.digitalbooking.digitalbooking.dto.CidadeDto;
import com.digitalbooking.digitalbooking.service.CidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("cidades")
public class CidadeController {

   @Autowired
   private CidadeService cidadeService;

   @Operation(summary = "Listar Cidades", description = "Retorna uma lista de cidades", tags = "Cidades")
   @ApiResponses(value = {
            @ApiResponse(
               responseCode = "200", description = "OK",
               content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CidadeDto.class))})   })
   @GetMapping
   public ResponseEntity <List<CidadeDto>> findAllCidades() {
      return cidadeService.findAllCidades();
   }


   @Operation(summary = "Criar uma Cidade", description = "Cria uma Cidade", tags = "Cidades")
   @ApiResponses(value = {
            @ApiResponse(
               responseCode = "201", description = "OK",
               content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CidadeDto.class))}),
            @ApiResponse(
               responseCode = "400", description = "Não foi possível salvar a cidade.",
               content = {@Content(mediaType = "application/json", schema = @Schema(hidden = true))})  })
   @PostMapping
   @ResponseBody
   public ResponseEntity<CidadeDto> saveCidade(@RequestBody CidadeDto cidadeDto){
      return cidadeService.saveCidade(cidadeDto);
   }
}
