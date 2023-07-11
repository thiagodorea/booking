package com.digitalbooking.digitalbooking.config;

import com.digitalbooking.digitalbooking.domain.Usuario;
import com.digitalbooking.digitalbooking.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

   @Autowired
   TokenService tokenService;

   @Autowired
   UsuarioRepository usuarioRepository;

   @Override
   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
      String token = recuperarToken(request);
      boolean valido = tokenService.verificaToken(token);
      if(valido)
         autenticarUsuario(token);
      filterChain.doFilter(request, response);
   }

   private void autenticarUsuario(String token) {
      String username =  tokenService.getUsername(token);
      Optional<Usuario> usuario = usuarioRepository.findByEmail(username);
      UsernamePasswordAuthenticationToken autenticationToken = new UsernamePasswordAuthenticationToken(usuario,null,usuario.get().getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(autenticationToken);
   }

   private String recuperarToken(HttpServletRequest request) {
      String getToken = request.getHeader("Authorization");
      if(getToken == null || getToken.isEmpty() || !getToken.startsWith("Bearer ")){
         return null;
      }
      return getToken.substring(7, getToken.length());
   }
}
