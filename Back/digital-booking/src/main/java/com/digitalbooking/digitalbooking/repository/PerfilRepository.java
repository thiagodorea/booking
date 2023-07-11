package com.digitalbooking.digitalbooking.repository;

import com.digitalbooking.digitalbooking.domain.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {

}
