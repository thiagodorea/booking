package com.digitalbooking.digitalbooking.domain;

import com.digitalbooking.digitalbooking.dto.CategoriaDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "categorias",uniqueConstraints = {@UniqueConstraint(columnNames = "uid")})
public class Categoria implements Serializable {
   private static final long serialVersionUID = 1L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   @NotNull(message = "Falta informar o UID da categoria.")
   private Long uid;
   @NotBlank(message = "Falta informar a descrição da categoria.")
   private String descricao;
   @NotBlank(message = "Falta a url da imagem da categoria.")
   private String urlImagem;
   @OneToMany(mappedBy = "categoria")
   @JsonIgnore
   private Set<Produto> produtoLista = new HashSet<>();


   public Categoria(CategoriaDto dto) {
      this.uid = dto.getUid();
      this.descricao = dto.getDescricao();
      this.urlImagem = dto.getUrlImagem();
   }
}
