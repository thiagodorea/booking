package com.digitalbooking.digitalbooking.service.impl;

import com.digitalbooking.digitalbooking.config.StorageProperties;
import com.digitalbooking.digitalbooking.domain.FileReference;
import com.digitalbooking.digitalbooking.domain.UploadRequestResult;
import com.digitalbooking.digitalbooking.repository.FileReferenceRepository;
import com.digitalbooking.digitalbooking.service.CloudStorageProvider;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Objects;

@Service
@AllArgsConstructor
public class StorageService {

   @Autowired
   private StorageProperties storageProperties;

   private final FileReferenceRepository fileReferenceRepository;

   private final CloudStorageProvider cloudStorageProvider;
   public UploadRequestResult generateUploadUrl(FileReference fileReference) {
      Objects.requireNonNull(fileReference);
      fileReferenceRepository.save(fileReference);
      fileReference.setDownloadUrl(storageProperties.getImage().getDownloadUrl().toString() + "/" + fileReference.getPath());
      fileReferenceRepository.save(fileReference);
      URL presignedUploadUrl =  cloudStorageProvider.generatePresignedUploadUrl(fileReference);
      return new UploadRequestResult(fileReference.getId(), presignedUploadUrl.toString());
   }
}
