package com.digitalbooking.digitalbooking.dto;


import com.digitalbooking.digitalbooking.domain.Imagem;
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
public class ImagemDto {
   private Long uid;
   private String titulo;
   private String url;

}
