package com.digitalbooking.digitalbooking.domain;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class UploadRequestResult {
   private Long fileReferenceId;
   private String uploadSignedUrl;

}
