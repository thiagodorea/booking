package com.digitalbooking.digitalbooking.repository;

import com.digitalbooking.digitalbooking.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
   Optional<Produto> findByUid(Long uid);
   List<Produto> findProdutoByCategoriaUid(Long uid);
   List<Produto> findProdutoByCidadeUid(Long uid);
   List<Produto> findProdutoByCidadeUidAndCategoriaUid(Long cidade, Long categoria);

   @Query("SELECT p FROM Produto p INNER JOIN p.reservasLista r INNER JOIN r.usuario u WHERE u.id = :clienteId" )
   List<Produto> findProdutoByUsuarioNum(Long clienteId);

   @Query("SELECT p FROM Produto p WHERE p.cidade.uid = :cidade AND p.id not in  (SELECT p.id FROM Produto p LEFT JOIN p.reservasLista r WHERE r.dataInicioReserva BETWEEN :dataInicial AND :dataFinal  OR  r.dataFinalReserva BETWEEN :dataInicial AND :dataFinal )" )
   List<Produto> findProdutoByCidadeOrCategoriaAndDataReserva(Long cidade, LocalDate dataInicial, LocalDate dataFinal);

   @Query("SELECT p FROM Produto p WHERE p.id not in  (SELECT p.id FROM Produto p LEFT JOIN p.reservasLista r WHERE r.dataInicioReserva BETWEEN :dataInicial AND :dataFinal  OR  r.dataFinalReserva BETWEEN :dataInicial AND :dataFinal )" )
   List<Produto> findProdutoByDataReserva( LocalDate dataInicial, LocalDate dataFinal);
}
