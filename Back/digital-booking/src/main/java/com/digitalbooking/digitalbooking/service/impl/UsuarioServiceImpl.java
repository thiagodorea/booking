package com.digitalbooking.digitalbooking.service.impl;

import com.digitalbooking.digitalbooking.config.TokenService;
import com.digitalbooking.digitalbooking.domain.Caracteristica;
import com.digitalbooking.digitalbooking.domain.Perfil;
import com.digitalbooking.digitalbooking.domain.Usuario;
import com.digitalbooking.digitalbooking.dto.PerfilDto;
import com.digitalbooking.digitalbooking.dto.TokenDto;
import com.digitalbooking.digitalbooking.dto.UsuarioDto;
import com.digitalbooking.digitalbooking.dto.UsuarioNovoDto;
import com.digitalbooking.digitalbooking.repository.UsuarioRepository;
import com.digitalbooking.digitalbooking.service.PerfilService;
import com.digitalbooking.digitalbooking.service.UsuarioService;
import jakarta.transaction.TransactionalException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UncheckedIOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class UsuarioServiceImpl implements UsuarioService {

   @Autowired
   private UsuarioRepository usuarioRepository;
   @Autowired
   private PerfilService perfilService;
   @Autowired
   private AuthenticationManager authenticationManager;
   @Autowired
   private TokenService tokenService;

   BCryptPasswordEncoder enc = new BCryptPasswordEncoder();

   @Transactional
   @Override
   public ResponseEntity saveUsuario(UsuarioNovoDto usuarioNovoDto) {
      try{
         if(usuarioNovoDto.getPerfis().size() == 0){
            List<PerfilDto> perfil = new ArrayList<>();
            perfil.add(PerfilDto.builder().nome("USER").build());
            usuarioNovoDto.setPerfis(perfil); ;
         }
         List<Perfil> perfis = perfilService.findAllPerfil().stream()
                  .filter(perfil -> usuarioNovoDto.getPerfis().stream().map(usuarioNovoPerfil -> usuarioNovoPerfil.getNome()).anyMatch(nome -> nome.equals(perfil.getNome()))).collect(Collectors.toList());
         Usuario usuario = toMapUsuario((usuarioNovoDto));
         usuario.setPerfis(perfis);
         if(usuarioRepository.findByEmail(usuario.getEmail()).isPresent())
            return new ResponseEntity("Usuário já existe. ", HttpStatus.CONFLICT);
         try {
            Usuario usuarioCadastrado = usuarioRepository.save(usuario);
            UsuarioDto usuarioDto = new UsuarioDto(usuarioNovoDto.getEmail(),usuarioNovoDto.getSenha());
            UsernamePasswordAuthenticationToken login = usuarioDto.converter();
            Authentication authentication = authenticationManager.authenticate(login);
            String token = tokenService.gerarToken(authentication);
            TokenDto tokenDto = new TokenDto(token, "Bearer");
            return new ResponseEntity(tokenDto, HttpStatus.CREATED);
         }catch (Exception e){
            return new ResponseEntity("Usuário cadastrado,porém não conseguimos autenticar o usuario, tente novamente mais tarde.", HttpStatus.BAD_REQUEST);
         }
      } catch (Exception e){
         return new ResponseEntity("Infelizmente não foi possível registrar. Por favor, tente novamente mais tarde. ", HttpStatus.BAD_REQUEST);
      }
   }

   private Usuario toMapUsuario(UsuarioNovoDto usuarioNovoDto){
      return Usuario.builder()
               .nome(usuarioNovoDto.getNome())
               .sobrenome(usuarioNovoDto.getSobrenome())
               .email(usuarioNovoDto.getEmail().toLowerCase())
               .senha(enc.encode( usuarioNovoDto.getSenha().toLowerCase()) )
               .perfis(usuarioNovoDto.getPerfis().stream().map(perfilDto -> Perfil.builder()
                        .nome(perfilDto.getNome()).build()).collect(Collectors.toList()))
               .build();
   }

   private UsuarioNovoDto toMapUsuarioNovoDto(Usuario usuario) {
      return UsuarioNovoDto.builder()
               .nome(usuario.getNome())
               .sobrenome(usuario.getSobrenome())
               .email(usuario.getEmail().toLowerCase())
               .senha(enc.encode(usuario.getSenha().toLowerCase()))
               .perfis(usuario.getPerfis().stream().map(perfil -> PerfilDto.builder()
                        .nome(perfil.getNome()).build()).collect(Collectors.toList()))
               .build();
   }

}
