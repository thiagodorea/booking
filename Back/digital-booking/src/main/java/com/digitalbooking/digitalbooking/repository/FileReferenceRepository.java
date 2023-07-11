package com.digitalbooking.digitalbooking.repository;

import com.digitalbooking.digitalbooking.domain.FileReference;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FileReferenceRepository extends JpaRepository<FileReference, Long> {
}
