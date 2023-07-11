package com.digitalbooking.digitalbooking.repository;

import com.digitalbooking.digitalbooking.domain.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
   Optional<Reserva> findByUid(Long uid);
   List<Reserva> findByUsuarioEmail(String clienteEmail);
}
