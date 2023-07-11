package com.digitalbooking.digitalbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ReservaDto {
   private Long uid;
   private LocalDateTime horaInicioReserva;
   private LocalDate dataInicioReserva;
   private LocalDate dataFinalReserva;
   private ProdutoDto produto;
   private ClienteDto cliente;
}
