package com.digitalbooking.digitalbooking.domain;

import com.digitalbooking.digitalbooking.dto.CidadeDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name = "cidades")
public class Cidade implements Serializable {
   private static final long serialVersionUID = 1L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   @NotNull(message = "Falta informar o UID da cidade.")
   private Long uid;
   @Column(name = "nome",unique = true)
   @NotBlank(message = "Falta informar o nome da cidade.")
   private String nome;
   @NotBlank(message = "Falta informar o pais.")
   private String pais;
   @OneToMany(mappedBy = "cidade")
   private Set<Produto> produtoLista = new HashSet<>();


   public Cidade(CidadeDto dto) {
      this.uid = dto.getUid();
      this.nome = dto.getNome();
      this.pais = dto.getPais();
   }
}
