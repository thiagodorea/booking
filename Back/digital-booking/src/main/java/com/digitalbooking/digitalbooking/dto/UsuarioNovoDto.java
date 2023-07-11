package com.digitalbooking.digitalbooking.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioNovoDto {
   @NotBlank
   @Size(min = 3, max = 50)
   private String nome;
   @NotBlank
   @Size(min = 3, max = 50)
   private String sobrenome;
   @NotBlank
   @Size(min = 3, max = 200)
   private String email;
   @NotBlank
   @Size(min = 6, max = 90)
   private String senha;
   @NotBlank(message = "Informe o perfil do usuario")
   private List<PerfilDto> perfis;

}
