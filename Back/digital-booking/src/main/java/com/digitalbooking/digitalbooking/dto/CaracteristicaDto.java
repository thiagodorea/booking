package com.digitalbooking.digitalbooking.dto;


import com.digitalbooking.digitalbooking.domain.Caracteristica;
import com.digitalbooking.digitalbooking.domain.Produto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CaracteristicaDto {
   private Long uid;
   private String nome;
   private String icone;

}
