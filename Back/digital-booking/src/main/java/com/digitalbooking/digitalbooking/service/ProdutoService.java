package com.digitalbooking.digitalbooking.service;

import com.digitalbooking.digitalbooking.dto.ClienteDto;
import com.digitalbooking.digitalbooking.dto.ProdutoDto;
import com.digitalbooking.digitalbooking.dto.ProdutoNovoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public interface ProdutoService {
   ResponseEntity<List<ProdutoDto>> findAllProdutos();
   ResponseEntity<List<ProdutoDto>> findProdutoByReservaUsuarioNum(Long Num);
   ResponseEntity<List<ProdutoDto>> findProdutoByCidadeOrCategoriaOrData(Long cidade, LocalDate dataInicial, LocalDate dataFinal);
   ResponseEntity<List<ProdutoDto>> findProdutoByCategoriaUid(Long uid);
   ResponseEntity<List<ProdutoDto>> findProdutoByCidadeUid(Long uid);
   ResponseEntity<Optional<ProdutoDto>> findProdutoByUid(Long uid);
   ResponseEntity<ProdutoNovoDto> saveProduto(ProdutoNovoDto produtoNovoDto);
   ResponseEntity<ProdutoNovoDto> updateProduto(ProdutoNovoDto produtoNovoDto);
   ResponseEntity<String> deleteProdutoByUid(Long uid);
}
