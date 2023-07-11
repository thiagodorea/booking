package com.digitalbooking.digitalbooking.controller;

import com.digitalbooking.digitalbooking.dto.UsuarioNovoDto;
import com.digitalbooking.digitalbooking.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.UnexpectedTypeException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("usuarios")
public class UsuarioController {

   @Autowired
   private UsuarioService usuarioService;

   @Operation(summary = "Criar um Usuário", description = "Cria um Usuario", tags = "Usuários")
   @ApiResponses(value = {
            @ApiResponse(
                     responseCode = "201", description = "OK",
                     content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioNovoDto.class))}),
            @ApiResponse(
                     responseCode = "400", description = "Não foi possível criar o Usuário",
                     content = {@Content(mediaType = "application/json", schema = @Schema(hidden = true))})  })
   @PostMapping()
   public ResponseEntity novoUsuario(@RequestBody UsuarioNovoDto usuarioNovoDto) throws UnexpectedTypeException {
      return usuarioService.saveUsuario(usuarioNovoDto);
   }
}
