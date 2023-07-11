package com.digitalbooking.digitalbooking.controller;

import com.digitalbooking.digitalbooking.domain.UploadImagemRequest;
import com.digitalbooking.digitalbooking.service.impl.StorageService;
import com.digitalbooking.digitalbooking.domain.UploadRequestResult;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/uploads")
@AllArgsConstructor
public class UploadController {

   private final StorageService storageService;
   @PostMapping("/imagens")
   public UploadRequestResult novaImagemUploadRequest(@RequestBody @Valid UploadImagemRequest uploadImagemRequest){
      return this.storageService.generateUploadUrl(uploadImagemRequest.toDomain());
   }
}
