package com.digitalbooking.digitalbooking.infra;

import com.digitalbooking.digitalbooking.config.StorageProperties;
import com.digitalbooking.digitalbooking.domain.FileReference;
import com.digitalbooking.digitalbooking.service.CloudStorageProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.awscore.AwsRequestOverrideConfiguration;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.net.URL;
import java.time.Duration;

@Component
@AllArgsConstructor
public class S3CloudStorageProvider implements CloudStorageProvider {

   private final S3Client s3Client;
   private final S3Presigner s3Presigner;
   private final StorageProperties storageProperties;
   @Override
   public URL generatePresignedUploadUrl(FileReference fileReference) {
      var builder = AwsRequestOverrideConfiguration.builder();

      if(fileReference.isPublico()){
         builder.putRawQueryParameter("x-amz-acl", "public-read");
      }

      PutObjectRequest objectRequest = PutObjectRequest.builder()
               .bucket(getBucket())
               .key(fileReference.getPath())
               .contentType(fileReference.getContentType())
               .contentLength(fileReference.getContentLength())
               .acl(fileReference.isPublico() ? "public-read" : null)
               .overrideConfiguration(builder.build())
               .build();


      PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
               .signatureDuration(Duration.ofMinutes(30))
               .putObjectRequest(objectRequest)
               .build();
      return s3Presigner.presignPutObject(presignRequest).url();
   }

   private String getBucket() {
      return storageProperties.getS3().getBucket();
   }
}
