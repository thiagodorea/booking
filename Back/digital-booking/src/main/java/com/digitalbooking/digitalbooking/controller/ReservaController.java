package com.digitalbooking.digitalbooking.controller;

import com.digitalbooking.digitalbooking.dto.ClienteDto;
import com.digitalbooking.digitalbooking.dto.ProdutoDto;
import com.digitalbooking.digitalbooking.dto.ReservaDto;
import com.digitalbooking.digitalbooking.dto.UsuarioDto;
import com.digitalbooking.digitalbooking.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("reservas")
public class ReservaController {

   @Autowired
   private ReservaService reservaService;

   @Operation(summary = "Listar Reservas por Usuario", description = "Retorna uma lista de Reservas por Usuario", tags = "Reservas")
   @ApiResponses(value = {
            @ApiResponse(
                     responseCode = "200", description = "OK",
                     content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoDto.class))})   })
   @PostMapping("/usuario")
   public ResponseEntity<List<ReservaDto>> findReservaByUsuarioEmail(@RequestBody ClienteDto clienteDto){
      return reservaService.findReservaByUsuarioEmail(clienteDto);
   }
   @Operation(summary = "Retornar uma Reserva", description = "Retorna uma Reservas pelo UID", tags = "Reservas")
   @Parameter(required = true, description = "Informe um UID de uma Reserva",name = "uid", example = "2305181031025")
   @ApiResponses(value = {
            @ApiResponse(
               responseCode = "200", description = "Retorna uma Reserva ou null",
               content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ReservaDto.class))}),
            @ApiResponse(
               responseCode = "404", description = "Reserva 'uid' não encontrato.",
               content = {@Content(mediaType = "application/json", schema = @Schema(hidden = true))})  })
   @GetMapping("{uid}")
   public ResponseEntity<Optional<ReservaDto>> findReservaByUid(@PathVariable Long uid){
      return reservaService.findReservaByUid(uid);
   }

   @Operation(summary = "Criar uma Reserva", description = "Cria uma Reserva", tags = "Reservas")
   @ApiResponses(value = {
            @ApiResponse(
                     responseCode = "201", description = "OK",
                     content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoDto.class))}),
            @ApiResponse(
                     responseCode = "400", description = "Não foi possível criar uma reserva",
                     content = {@Content(mediaType = "application/json", schema = @Schema(hidden = true))})  })
   @PostMapping
   @ResponseBody
   public ResponseEntity<ReservaDto> saveReserva(@RequestBody ReservaDto reservaDto){
      return reservaService.saveReserva(reservaDto);
   }

}
