package com.digitalbooking.digitalbooking.config;

import com.digitalbooking.digitalbooking.domain.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.security.KeyRep;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {

   @Value("${db.jwt.expiration}")
   private String expiracao;
   @Value("${db.jwt.secret}")
   private String secret;

   public String gerarToken(Authentication authentication) {
      Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
      Date hoje = new Date();
      Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiracao));
      HashMap<String, String> hashHeader = new HashMap<String, String>();
      hashHeader.put("typ", "JWT");
      Map<String, Object> claims = new HashMap<>();
      claims.put("num",usuarioLogado.getId()) ;
      claims.put("nome",usuarioLogado.getNome()) ;
      claims.put("sobrenome",usuarioLogado.getSobrenome());
      claims.put("email",usuarioLogado.getEmail());
      claims.put("funcao",usuarioLogado.getPerfis().get(0).getNome());
      return Jwts.builder()
               .setHeaderParam("typ","JWT")
               .setIssuer("Api Digital Booking")
               .addClaims(claims)
               .setSubject(usuarioLogado.getUsername())
               .setIssuedAt(new Date())
               .setExpiration(dataExpiracao)
               .signWith(SignatureAlgorithm.HS256,secret)
               .compact();
   }

   public boolean verificaToken(String token) {
      try {
         Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
         return true;
      }catch (Exception e)  {
         return false;
      }
   }

   public String getUsername(String token) {
      Claims claims =  Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
      String username = claims.getSubject();
      return username;
   }
}
