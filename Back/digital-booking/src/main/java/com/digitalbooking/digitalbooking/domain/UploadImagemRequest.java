package com.digitalbooking.digitalbooking.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UploadImagemRequest {

   @NotBlank
   private String fileName;

   @NotBlank
   private String contentType;

   @NotNull
   @Min(1)
   private Long ContentLength;

   public FileReference toDomain() {
      return FileReference.builder()
               .nome(this.fileName)
               .contentType(this.contentType)
               .contentLength(this.ContentLength)
               .type(FileReference.Type.IMAGE)
               .build();
   }
}
