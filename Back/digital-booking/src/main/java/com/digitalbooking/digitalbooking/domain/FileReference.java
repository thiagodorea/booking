package com.digitalbooking.digitalbooking.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.Objects;


@Setter
@Getter
@EqualsAndHashCode
@ToString
@Builder
@Entity
public class FileReference {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @CreationTimestamp
   private OffsetDateTime dataCriacao;

   private String nome;

   private String contentType;

   private Long contentLength;

   @Builder.Default
   private boolean temp = true;

   @Enumerated(EnumType.STRING)
   private Type type;

   private String downloadUrl;

   protected FileReference() {
   }

   public FileReference(Long id, OffsetDateTime dataCriacao, String nome, String contentType, Long contentLength, boolean temp, Type type, String downloadUrl) {
      Objects.requireNonNull(nome);
      Objects.requireNonNull(contentType);
      Objects.requireNonNull(contentLength);
      Objects.requireNonNull(type);
      this.id = id;
      this.dataCriacao = dataCriacao;
      this.nome = nome;
      this.contentType = contentType;
      this.contentLength = contentLength;
      this.temp = temp;
      this.type = type;
      this.downloadUrl = downloadUrl;
   }

   public void setTemp(boolean temp) {
      this.temp = temp;
   }

   public String getPath() {
      return this.id+"/"+this.nome;
   }

   @Getter
   @AllArgsConstructor
   public enum Type {
      DOCUMENT(false),
      IMAGE(true);
      private final boolean publico;
   }

   public boolean isPublico() {
      return this.type.isPublico();
   }
}
