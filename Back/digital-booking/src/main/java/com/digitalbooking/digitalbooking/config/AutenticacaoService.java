package com.digitalbooking.digitalbooking.config;

import com.digitalbooking.digitalbooking.domain.Usuario;
import com.digitalbooking.digitalbooking.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class AutenticacaoService implements UserDetailsService {

   @Autowired
   UsuarioRepository repository;

   @Override
   public UserDetails loadUserByUsername(String email) {
      Usuario usuario = repository.findByEmail(email)
            .orElseThrow(() ->  new UsernameNotFoundException("Usuário não encontrado."));
      return usuario;
   }
}
