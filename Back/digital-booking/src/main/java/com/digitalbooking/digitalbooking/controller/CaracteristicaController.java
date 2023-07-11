package com.digitalbooking.digitalbooking.controller;

import com.digitalbooking.digitalbooking.domain.Caracteristica;
import com.digitalbooking.digitalbooking.dto.CaracteristicaDto;
import com.digitalbooking.digitalbooking.dto.CategoriaDto;
import com.digitalbooking.digitalbooking.service.CaracteristicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("caracteristicas")
public class CaracteristicaController {

   @Autowired
   private CaracteristicaService caracteristicaService;

   @Operation(summary = "Listar Caracteristica", description = "Retorna uma lista de Caracteristica", tags = "Caracteristica")
   @ApiResponses(value = {
            @ApiResponse(
               responseCode = "200", description = "OK",
               content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CategoriaDto.class))})   })
   @GetMapping
   public List<CaracteristicaDto> findAllCaracteristica(){
      return caracteristicaService.findAllCaracteristicaDTO();
   }


}
