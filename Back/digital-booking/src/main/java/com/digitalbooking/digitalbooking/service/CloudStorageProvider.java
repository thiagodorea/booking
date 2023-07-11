package com.digitalbooking.digitalbooking.service;

import com.digitalbooking.digitalbooking.domain.FileReference;

import java.net.URL;

public interface CloudStorageProvider {
   URL generatePresignedUploadUrl(FileReference fileReference);
}
