package com.digitalbooking.digitalbooking.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {

   @NotBlank
   @Size(min = 6, max =200)
   private String email;
   @NotBlank
   @Size(min = 6, max =90)
   private String senha;

   public UsernamePasswordAuthenticationToken converter(){
      return new UsernamePasswordAuthenticationToken(email, senha);
   }
}
