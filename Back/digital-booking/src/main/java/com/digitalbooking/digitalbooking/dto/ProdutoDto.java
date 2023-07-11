package com.digitalbooking.digitalbooking.dto;


import com.digitalbooking.digitalbooking.domain.FileReference;
import com.digitalbooking.digitalbooking.domain.Produto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ProdutoDto {
   private Long uid;
   private String nome;
   private String descricao;
   private Double latitude;
   private Double longitude;
   private String endereco;
   private Integer qualificacao;
   private Integer classificacao;
   private List<CaracteristicaDto> caracteristicasLista;
   private List<ImagemDto> imagensLista;
   private List<FileReference> imagens;
   private CategoriaDto categoria;
   private CidadeDto cidade;
   private String regras;
   private String saudeSeguranca;
   private String politicaCancelamento;
   private List<ReservaDto> reservasLista;

}
