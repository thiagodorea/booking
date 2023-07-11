package com.digitalbooking.digitalbooking.domain;

import com.digitalbooking.digitalbooking.dto.CidadeDto;
import com.digitalbooking.digitalbooking.dto.ReservaDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
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
@Table(name = "reservas")
public class Reserva implements Serializable {
   private static final long serialVersionUID = 1L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   @Column(unique = true)
   @NotNull(message = "Falta informar o UID da cidade.")
   private Long uid;
   @NotNull(message = "Falta informar a hora de inicio da reserva.")
   private LocalDateTime horaInicioReserva;
   @NotNull(message = "Falta informar a data de inicio da reserva.")
   private LocalDate dataInicioReserva;
   @NotNull(message = "Falta informar a data de final da reserva.")
   private LocalDate dataFinalReserva;
   @ManyToOne
   @JoinColumn(name = "produto_id", nullable = false)
   private Produto produto;
   @ManyToOne
   @JoinColumn(name = "usuario_id", nullable = false)
   private Usuario usuario;

}
