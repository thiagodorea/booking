package com.digitalbooking.digitalbooking.config;

import com.digitalbooking.digitalbooking.dto.TokenDto;
import com.digitalbooking.digitalbooking.dto.UsuarioDto;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AutenticacaoController {

   @Autowired
   private AuthenticationManager authenticationManager;
   @Autowired
   private TokenService tokenService;

   @PostMapping
   public ResponseEntity autenticar(@RequestBody UsuarioDto usuarioDto) throws UsernameNotFoundException, AuthenticationException {
      try {
         UsernamePasswordAuthenticationToken login = usuarioDto.converter();
         Authentication authentication = authenticationManager.authenticate(login);
         String token = tokenService.gerarToken(authentication);
         TokenDto tokenDto = new TokenDto(token, "Bearer");
         return new ResponseEntity(tokenDto, HttpStatus.OK);
      }catch (AuthenticationException e){
         return new ResponseEntity("Por favor, tente novamente, suas credenciais são inválidas.",HttpStatus.BAD_REQUEST);
      }
   }
}
