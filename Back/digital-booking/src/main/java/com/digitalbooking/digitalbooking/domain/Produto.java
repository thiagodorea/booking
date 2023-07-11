package com.digitalbooking.digitalbooking.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "produtos")
public class Produto implements Serializable {
   private static final long serialVersionUID = 1L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "uid",unique = true)
   @NotNull(message = "Falta informar o UID do produto.")
   private Long uid;

   @NotBlank(message = "Falta informar o nome do produto.")
   private String nome;

   @NotBlank(message = "Falta informar a descrição do produto.")
   @Column(length = 512)
   private String descricao;

   @NotNull(message = "Falta informar a latitude.")
   private Double latitude;

   @NotNull(message = "Falta informar a longitude.")
   private Double longitude;

   private String endereco;

   @NotNull(message = "Falta informar a qualificação.")
   private Integer qualificacao;
   @NotNull(message = "Falta informar a classificação.")
   private Integer classificacao;

   @ManyToMany(fetch = FetchType.EAGER)
   @JoinTable(name = "produtos_caracteristicas",
      joinColumns = @JoinColumn(name = "produto_id"),
      inverseJoinColumns = @JoinColumn(name = "caracteristica_id"))
   private List<Caracteristica> caracteristicasLista;

   @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
   @JoinTable(name = "produto_imagens",
      joinColumns = @JoinColumn(name = "produto_id"),
      inverseJoinColumns = @JoinColumn(name = "imagem_id"))
   private List<Imagem> imagensLista;

   @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
   @JoinTable(name = "produto_imagens_aws",
      joinColumns = @JoinColumn(name = "produto_id"),
      inverseJoinColumns = @JoinColumn(name = "file_reference_id"))
   private List<FileReference> imagens;

   @ManyToOne
   @JoinColumn(name = "categoria_id", nullable = false)
   private Categoria categoria;

   @ManyToOne
   @JoinColumn(name = "cidade_id", nullable = false)
   private Cidade cidade;

   private String regras;

   private String saudeSeguranca;

   private String politicaCancelamento;

   @OneToMany(mappedBy = "produto",fetch = FetchType.EAGER)
   private List<Reserva> reservasLista;
}
