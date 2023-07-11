package com.digitalbooking.digitalbooking.domain;

import com.digitalbooking.digitalbooking.dto.CaracteristicaDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "caracteristicas")
public class Caracteristica implements Serializable {
   private static final long serialVersionUID = 1L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   @NotNull(message = "Falta informar o UID da caracteristica.")
   private Long uid;
   @NotBlank(message = "Falta informar o nome do caracteristica.")
   private String nome;
   @NotBlank(message = "Falta informar o icone do caracteristica.")
   private String icone;

}
