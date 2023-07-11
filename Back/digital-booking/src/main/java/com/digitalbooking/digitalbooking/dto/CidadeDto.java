package com.digitalbooking.digitalbooking.dto;


import com.digitalbooking.digitalbooking.domain.Cidade;
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
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CidadeDto {
   private Long uid;
   private String nome;
   private String pais;

   public CidadeDto(Cidade entity) {
      this.uid = entity.getUid();
      this.nome = entity.getNome();
      this.pais = entity.getPais();
   }
}
