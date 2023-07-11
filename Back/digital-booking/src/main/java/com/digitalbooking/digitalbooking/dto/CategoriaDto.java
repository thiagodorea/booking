package com.digitalbooking.digitalbooking.dto;

import com.digitalbooking.digitalbooking.domain.Categoria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CategoriaDto {
   private Long uid;
   private String descricao;
   private String urlImagem;

   public CategoriaDto(Categoria entity) {
      this.uid = entity.getUid();
      this.descricao = entity.getDescricao();
      this.urlImagem = entity.getUrlImagem();
   }
}

