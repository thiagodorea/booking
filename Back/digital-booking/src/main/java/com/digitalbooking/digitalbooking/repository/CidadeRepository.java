package com.digitalbooking.digitalbooking.repository;

import com.digitalbooking.digitalbooking.domain.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {
   Optional<Cidade> findByUid(Long uid);
}
