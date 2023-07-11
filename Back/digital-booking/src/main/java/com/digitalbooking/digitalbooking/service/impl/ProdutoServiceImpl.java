package com.digitalbooking.digitalbooking.service.impl;

import com.digitalbooking.digitalbooking.config.StorageProperties;
import com.digitalbooking.digitalbooking.domain.Caracteristica;
import com.digitalbooking.digitalbooking.domain.Categoria;
import com.digitalbooking.digitalbooking.domain.Cidade;
import com.digitalbooking.digitalbooking.domain.FileReference;
import com.digitalbooking.digitalbooking.domain.Imagem;
import com.digitalbooking.digitalbooking.domain.Produto;
import com.digitalbooking.digitalbooking.domain.Reserva;
import com.digitalbooking.digitalbooking.domain.Usuario;
import com.digitalbooking.digitalbooking.dto.CaracteristicaDto;
import com.digitalbooking.digitalbooking.dto.CategoriaDto;
import com.digitalbooking.digitalbooking.dto.CidadeDto;
import com.digitalbooking.digitalbooking.dto.ClienteDto;
import com.digitalbooking.digitalbooking.dto.ImagemDto;
import com.digitalbooking.digitalbooking.dto.ProdutoDto;
import com.digitalbooking.digitalbooking.dto.ProdutoNovoDto;
import com.digitalbooking.digitalbooking.dto.ReservaDto;
import com.digitalbooking.digitalbooking.repository.FileReferenceRepository;
import com.digitalbooking.digitalbooking.repository.ProdutoRepository;
import com.digitalbooking.digitalbooking.service.CaracteristicaService;
import com.digitalbooking.digitalbooking.service.CategoriaService;
import com.digitalbooking.digitalbooking.service.CidadeService;
import com.digitalbooking.digitalbooking.service.ProdutoService;
import com.digitalbooking.digitalbooking.utils.Utils;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class ProdutoServiceImpl implements ProdutoService {

   @Autowired
   private ProdutoRepository produtoRepository;
   @Autowired
   private CategoriaService categoriaService;
   @Autowired
   private CidadeService cidadeService;
   @Autowired
   private CaracteristicaService caracteristicaService;
   @Autowired
   private FileReferenceRepository fileReferenceRepository;
   @Autowired
   private StorageProperties storageProperties;

   @Override
   @Transactional(readOnly = true)
   public ResponseEntity<List<ProdutoDto>> findAllProdutos() {
      return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.findAll().stream().map(produto -> toMapProdutoDto(produto)).collect(Collectors.toList()));
   }

   @Override
   @Transactional(readOnly = true)
   public ResponseEntity<List<ProdutoDto>> findProdutoByReservaUsuarioNum(Long num) {
      return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.findProdutoByUsuarioNum(num).stream().map(produto -> toMapProdutoDto(produto)).collect(Collectors.toList()));
   }

   @Override
   public ResponseEntity<List<ProdutoDto>> findProdutoByCidadeOrCategoriaOrData(Long cidade, LocalDate dataInicial, LocalDate dataFinal) {
      if(cidade == null  && dataInicial != null && dataFinal != null) {
         return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.findProdutoByDataReserva( dataInicial, dataFinal).stream().map(produto -> toMapProdutoDto(produto)).collect(Collectors.toList()));
      }
      return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.findProdutoByCidadeOrCategoriaAndDataReserva(cidade, dataInicial, dataFinal).stream().map(produto -> toMapProdutoDto(produto)).collect(Collectors.toList()));
   }

   @Override
   @Transactional(readOnly = true)
   public ResponseEntity<List<ProdutoDto>> findProdutoByCategoriaUid(Long uid) {
      return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.findProdutoByCategoriaUid(uid).stream().map(produto -> toMapProdutoDto(produto)).collect(Collectors.toList()));
   }
   @Override
   @Transactional(readOnly = true)
   public ResponseEntity<List<ProdutoDto>> findProdutoByCidadeUid(Long uid) {
      return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.findProdutoByCidadeUid(uid).stream().map(produto -> toMapProdutoDto(produto)).collect(Collectors.toList()));
   }

   @Override
   @Transactional(readOnly = true)
   public ResponseEntity<Optional<ProdutoDto>> findProdutoByUid(Long uid) {
      try{
         Optional<ProdutoDto> produtoDto = produtoRepository.findByUid(uid).map(produto -> toMapProdutoDto(produto));
         produtoDto.orElseThrow(() -> new ObjectNotFoundException(produtoDto, ProdutoDto.class.getName()));
         return ResponseEntity.status(HttpStatus.OK).body(produtoDto);
      }catch(Exception e){
         return new ResponseEntity("Propriedade " +uid+" não encontrato.",HttpStatus.NOT_FOUND);
      }
   }

   @Override
   @Transactional
   public ResponseEntity<ProdutoNovoDto> saveProduto(ProdutoNovoDto produtoDto) {
      try{
         List<Caracteristica> caracteristicasProduto = caracteristicaService.findAllCaracteristica().stream()
                  .filter(c -> produtoDto.getCaracteristicasLista().stream().map(cp -> cp.getUid()).anyMatch(uid -> uid.equals(c.getUid()))).collect(Collectors.toList());
         Categoria categoria = categoriaService.findIdCategoriaByUid(produtoDto.getCategoria().getUid());
         Cidade cidade = cidadeService.findIdCidadeByUid(produtoDto.getCidade().getUid());
         List<FileReference> fileReferencesImages = produtoDto.getImagens().stream()
                  .map(reference -> fileReferenceRepository.findById(reference.getId())
                           .orElseThrow(() -> new NotFoundException(String.format("Imagem %s não localizada no AWS ", reference.getId())))
                  )
                  .collect(Collectors.toList());
         produtoDto.setUid(new Utils().gerarUid());
         produtoDto.setImagensLista(fileReferencesImages.stream().map(imagem -> ImagemDto.builder()
                  .uid(new Utils().gerarUid())
                  .titulo(imagem.getNome())
                  .url( imagem.getDownloadUrl()).build()).collect(Collectors.toList()));
         produtoDto.setImagens(fileReferencesImages);
         Produto produto = toMapProdutoNovo(produtoDto);
         produto.setCaracteristicasLista(caracteristicasProduto);
         produto.setCategoria(categoria);
         produto.setCidade(cidade);
         return ResponseEntity.status(HttpStatus.CREATED).body(toMapProdutoNovoDto(produtoRepository.save(produto)));
      }catch (ConstraintViolationException e){
         log.error("[ProdutoService] [saveProduto] Dados obrigatórios faltando: " + produtoDto.toString());
         List<String> msgViolacao = new ArrayList<>();
         e.getConstraintViolations().stream().map(violation -> msgViolacao.add( violation.getMessage() )).collect(Collectors.toList());
         return new ResponseEntity("Dados obrigatórios faltando: " +msgViolacao.get(0) , HttpStatus.BAD_REQUEST);
      }
   }

   @Override
   public ResponseEntity<ProdutoNovoDto> updateProduto(ProdutoNovoDto produtoNovoDto){

      try {
         Produto produto = produtoRepository.findByUid(produtoNovoDto.getUid()).get();

         List<Caracteristica> caracteristicasProduto = produto.getCaracteristicasLista().stream()
                  .flatMap(caracteristica -> produtoNovoDto.getCaracteristicasLista().stream()
                           .filter(caracteristicaDto -> caracteristicaDto.getUid().equals(caracteristica.getUid()))
                           .map(caracteristicaDto -> Caracteristica.builder()
                                 .id(caracteristica.getId())
                                 .uid(caracteristicaDto.getUid())
                                 .nome(caracteristicaDto.getNome())
                                 .icone(caracteristicaDto.getIcone())
                                 .build()))
                  .collect(Collectors.toList());

         Categoria categoria = Categoria.builder()
                  .id(produto.getCategoria().getId())
                  .uid(produtoNovoDto.getCategoria().getUid())
                  .descricao(produtoNovoDto.getCategoria().getDescricao())
                  .urlImagem(produtoNovoDto.getCategoria().getUrlImagem())
                  .build();

         Cidade cidade = Cidade.builder()
                  .id(produto.getCidade().getId())
                  .uid(produtoNovoDto.getCidade().getUid())
                  .nome(produtoNovoDto.getCidade().getNome())
                  .pais(produtoNovoDto.getCidade().getPais())
                  .build();


         List<FileReference> fileReferencesImages = produtoNovoDto.getImagens().stream()
               .map(reference -> fileReferenceRepository.findById(reference.getId())
                     .orElseThrow(() -> new NotFoundException(String.format("Imagem %s não localizada no AWS ", reference.getId())))
               )
               .collect(Collectors.toList());


         Produto produtoAtualizado = Produto.builder()
                  .id(produto.getId())
                  .uid(produtoNovoDto.getUid())
                  .nome(produtoNovoDto.getNome())
                  .descricao(produtoNovoDto.getDescricao())
                  .latitude(produtoNovoDto.getLatitude())
                  .longitude(produtoNovoDto.getLongitude())
                  .endereco(produtoNovoDto.getEndereco())
                  .qualificacao(produtoNovoDto.getQualificacao())
                  .classificacao(produtoNovoDto.getClassificacao())
                  .caracteristicasLista(caracteristicasProduto)
                  .imagens(buildImagensAwsProduto(fileReferencesImages, produto))
                  .imagensLista(buildImagensProduto(fileReferencesImages, produtoNovoDto, produto))
                  .categoria(categoria)
                  .cidade(cidade)
                  .regras(produtoNovoDto.getRegras())
                  .saudeSeguranca(produtoNovoDto.getSaudeSeguranca())
                  .politicaCancelamento(produtoNovoDto.getPoliticaCancelamento())
                  .build();
         Produto produtoretornoAtualizado = produtoRepository.save(produtoAtualizado);
         return ResponseEntity.status(HttpStatus.OK).body(toMapProdutoNovoDto(produtoretornoAtualizado));
      }catch (ConstraintViolationException e){
         log.error("[ProdutoService] [saveProduto] Dados obrigatórios faltando: " + produtoNovoDto.toString());
         List<String> msgViolacao = new ArrayList<>();
         e.getConstraintViolations().stream().map(violation -> msgViolacao.add( violation.getMessage() )).collect(Collectors.toList());
         return new ResponseEntity("Dados obrigatórios faltando: " +msgViolacao , HttpStatus.BAD_REQUEST);
      }catch (NotFoundException e) {
         log.error("[ProdutoService] ", e.getMessage());
         return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
      }catch (Exception e){
         log.error("[ProdutoService] [saveProduto] Dados obrigatórios faltando: " + produtoNovoDto.toString());
         return new ResponseEntity("Não foi possivel atualizar os dados da propriedade. ", HttpStatus.BAD_REQUEST);
      }
   }

   private static List<Imagem> buildImagensProduto(List<FileReference> fileReferencesImages, ProdutoNovoDto produtoNovoDto, Produto produto) {

      List <Imagem> imagensLista = produto.getImagensLista().stream()
               .flatMap(imagem -> produtoNovoDto.getImagensLista().stream()
                        .filter(imagemDto -> imagemDto.getUid().equals(imagem.getUid()))
                        .map(imagemDto -> Imagem.builder()
                              .id(imagem.getId())
                              .uid(imagemDto.getUid())
                              .titulo(imagemDto.getTitulo())
                              .url(imagemDto.getUrl())
                              .build()))
               .collect(Collectors.toList());

//      fileReferencesImages.stream().map(imagemReference -> imagensLista.stream().map( imagem -> Imagem.builder()
//              .uid(new Utils().gerarUid())
//              .titulo(imagemReference.getNome())
//              .url( imagemReference.getDownloadUrl()).build())).collect(Collectors.toList())
//               ;
//
//      List<Imagem> imagensProduto = imagensLista.stream()
//              .flatMap(imagem -> fileReferencesImages.stream()
//                      .filter(reference -> reference.getDownloadUrl().equals(imagem.getUrl()))
//                      .map(reference -> Imagem.builder()
//                              .id(imagem.getId())
//                              .uid(reference. getUid())
//                              .nome(reference.getNome())
//                              .icone(reference.getIcone())
//                              .build()))
//              .collect(Collectors.toList());

      return imagensLista;
   }

   private static List<FileReference> buildImagensAwsProduto(List<FileReference> fileReferencesImages, Produto produto) {

      if(Objects.isNull(produto.getImagens())){
         return fileReferencesImages;
      }else{
         return produto.getImagens().stream()
                  .flatMap(imagem -> fileReferencesImages.stream()
                           .filter(imagemDto -> imagemDto.getId().equals(imagem.getId()))
                           .map(imagemDto -> FileReference.builder()
                                 .id(imagem.getId())
                                 .dataCriacao(imagemDto.getDataCriacao())
                                 .nome(imagemDto.getNome())
                                 .contentType(imagemDto.getContentType())
                                 .contentLength(imagemDto.getContentLength())
                                 .temp(false)
                                 .type(imagemDto.getType())
                                 .downloadUrl(imagemDto.getDownloadUrl())
                                 .build()))
                  .collect(Collectors.toList());
      }

   }

   @Override
   public ResponseEntity<String> deleteProdutoByUid(Long uid) {
      try {
         Produto produto = responseProdutoByUid(uid);
         produtoRepository.deleteById(produto.getId());
         return ResponseEntity.status(HttpStatus.OK).body("Propriedade " + produto.getNome() + " excluida com sucesso.");
      } catch (NotFoundException e) {
         log.error("[ProdutoService] [deleteProdutoByUid] ", e.getMessage());
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
      } catch (Exception e) {
         log.error("[ProdutoService] [deleteProdutoByUid] Erro ao excluir Propriedade", e);
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao excluir o Propriedade");
      }
   }

   private Produto responseProdutoByUid(Long uid){
      return produtoRepository.findByUid(uid).orElseThrow(() -> new NotFoundException("UID não localizado"));
   }


   private Produto toMapProduto(ProdutoDto produtoDto){
      return Produto.builder()
               .uid(new Utils().gerarUid())
               .nome(produtoDto.getNome())
               .descricao(produtoDto.getDescricao())
               .latitude(produtoDto.getLatitude())
               .longitude(produtoDto.getLongitude())
               .endereco(produtoDto.getEndereco())
               .qualificacao(produtoDto.getQualificacao())
               .classificacao(produtoDto.getClassificacao())
               .caracteristicasLista(produtoDto.getCaracteristicasLista().stream().map(caracteristicaDto -> Caracteristica.builder()
                        .uid(caracteristicaDto.getUid())
                        .nome(caracteristicaDto.getNome())
                        .icone(caracteristicaDto.getIcone()).build()).collect(Collectors.toList()))
               .imagensLista(produtoDto.getImagensLista().stream().map(imagemDto -> Imagem.builder()
                        .uid(imagemDto.getUid())
                        .titulo(imagemDto.getTitulo())
                        .url(imagemDto.getUrl()).build()).collect(Collectors.toList()))
               .imagens(produtoDto.getImagens().stream().map(reference -> FileReference.builder()
                        .id(reference.getId())
                        .dataCriacao(reference.getDataCriacao())
                        .nome(reference.getNome())
                        .contentType(reference.getContentType())
                        .contentLength(reference.getContentLength())
                        .type(reference.getType())
                        .downloadUrl(reference.getDownloadUrl()).build()).collect(Collectors.toList()))
               .categoria(Categoria.builder().uid(produtoDto.getCategoria().getUid())
                        .descricao(produtoDto.getCategoria().getDescricao())
                        .urlImagem((produtoDto.getCategoria().getUrlImagem())).build())
               .cidade(Cidade.builder().uid(produtoDto.getCidade().getUid())
                        .nome(produtoDto.getCidade().getNome())
                        .pais(produtoDto.getCidade().getPais()).build())
               .regras(produtoDto.getRegras())
               .saudeSeguranca(produtoDto.getSaudeSeguranca())
               .politicaCancelamento(produtoDto.getPoliticaCancelamento())
               .reservasLista(produtoDto.getReservasLista().stream().map(reservaDto -> Reserva.builder()
                        .uid(reservaDto.getUid())
                        .dataInicioReserva(reservaDto.getDataInicioReserva())
                        .dataFinalReserva(reservaDto.getDataFinalReserva())
                        .horaInicioReserva(reservaDto.getHoraInicioReserva())
                        .usuario(Usuario.builder()
                                 .email(reservaDto.getCliente().getEmail())
                                 .nome(reservaDto.getCliente().getNome())
                                 .sobrenome(reservaDto.getCliente().getSobrenome()).build())
                        .build()).collect(Collectors.toList()))
               .build();
   }

   private Produto toMapProdutoNovo(ProdutoNovoDto produtoNovoDto){
      return Produto.builder()
               .uid(new Utils().gerarUid())
               .nome(produtoNovoDto.getNome())
               .descricao(produtoNovoDto.getDescricao())
               .latitude(produtoNovoDto.getLatitude())
               .longitude(produtoNovoDto.getLongitude())
               .endereco(produtoNovoDto.getEndereco())
               .qualificacao(produtoNovoDto.getQualificacao())
               .classificacao(produtoNovoDto.getClassificacao())
               .caracteristicasLista(produtoNovoDto.getCaracteristicasLista().stream().map(caracteristicaDto -> Caracteristica.builder()
                        .uid(caracteristicaDto.getUid())
                        .nome(caracteristicaDto.getNome())
                        .icone(caracteristicaDto.getIcone()).build()).collect(Collectors.toList()))
               .imagensLista(produtoNovoDto.getImagensLista().stream().map(imagemDto -> Imagem.builder()
                        .uid(imagemDto.getUid())
                        .titulo(imagemDto.getTitulo())
                        .url(imagemDto.getUrl()).build()).collect(Collectors.toList()))
               .imagens(produtoNovoDto.getImagens().stream().map(reference -> FileReference.builder()
                        .id(reference.getId())
                        .dataCriacao(reference.getDataCriacao())
                        .nome(reference.getNome())
                        .contentType(reference.getContentType())
                        .temp(false)
                        .contentLength(reference.getContentLength())
                        .type(reference.getType())
                        .downloadUrl(reference.getDownloadUrl()).build()).collect(Collectors.toList()))
               .categoria(Categoria.builder().uid(produtoNovoDto.getCategoria().getUid())
                        .descricao(produtoNovoDto.getCategoria().getDescricao())
                        .urlImagem((produtoNovoDto.getCategoria().getUrlImagem())).build())
               .cidade(Cidade.builder().uid(produtoNovoDto.getCidade().getUid())
                        .nome(produtoNovoDto.getCidade().getNome())
                        .pais(produtoNovoDto.getCidade().getPais()).build())
               .regras(produtoNovoDto.getRegras())
               .saudeSeguranca(produtoNovoDto.getSaudeSeguranca())
               .politicaCancelamento(produtoNovoDto.getPoliticaCancelamento())
               .build();
   }

   private ProdutoDto toMapProdutoDto(Produto produto){
      return ProdutoDto.builder()
               .uid(produto.getUid())
               .nome(produto.getNome())
               .descricao(produto.getDescricao())
               .latitude(produto.getLatitude())
               .longitude(produto.getLongitude())
               .endereco(produto.getEndereco())
               .qualificacao(produto.getQualificacao())
               .classificacao(produto.getClassificacao())
               .caracteristicasLista(produto.getCaracteristicasLista().stream().map(caracteristicaDto -> CaracteristicaDto.builder()
                        .uid(caracteristicaDto.getUid())
                        .nome(caracteristicaDto.getNome())
                        .icone(caracteristicaDto.getIcone()).build()).collect(Collectors.toList()))
               .imagensLista(produto.getImagensLista().stream().map(imagemDto -> ImagemDto.builder()
                        .uid(imagemDto.getUid())
                        .titulo(imagemDto.getTitulo())
                        .url(imagemDto.getUrl()).build()).collect(Collectors.toList()))
               .imagens(produto.getImagens().stream().map(reference -> FileReference.builder()
                        .id(reference.getId())
                        .dataCriacao(reference.getDataCriacao())
                        .nome(reference.getNome())
                        .contentType(reference.getContentType())
                        .contentLength(reference.getContentLength())
                        .type(reference.getType())
                        .downloadUrl(reference.getDownloadUrl()).build()).collect(Collectors.toList()))
               .categoria(CategoriaDto.builder().uid(produto.getCategoria().getUid())
                        .descricao(produto.getCategoria().getDescricao())
                        .urlImagem((produto.getCategoria().getUrlImagem())).build())
               .cidade(CidadeDto.builder().uid(produto.getCidade().getUid())
                        .nome(produto.getCidade().getNome())
                        .pais(produto.getCidade().getPais()).build())
               .regras(produto.getRegras())
               .saudeSeguranca(produto.getSaudeSeguranca())
               .politicaCancelamento(produto.getPoliticaCancelamento())
               .reservasLista(produto.getReservasLista().stream().map(reserva -> ReservaDto.builder()
                        .uid(reserva.getUid())
                        .dataInicioReserva(reserva.getDataInicioReserva())
                        .dataFinalReserva(reserva.getDataFinalReserva())
                        .horaInicioReserva(reserva.getHoraInicioReserva())
                        .cliente(ClienteDto.builder()
                                 .email(reserva.getUsuario().getEmail())
                                 .nome(reserva.getUsuario().getNome())
                                 .sobrenome(reserva.getUsuario().getSobrenome()).build())
                        .build()).collect(Collectors.toList()))
               .build();
   }
   private ProdutoNovoDto toMapProdutoNovoDto(Produto produto){
      return ProdutoNovoDto.builder()
               .uid(produto.getUid())
               .nome(produto.getNome())
               .descricao(produto.getDescricao())
               .latitude(produto.getLatitude())
               .longitude(produto.getLongitude())
               .endereco(produto.getEndereco())
               .qualificacao(produto.getQualificacao())
               .classificacao(produto.getClassificacao())
               .caracteristicasLista(produto.getCaracteristicasLista().stream().map(caracteristicaDto -> CaracteristicaDto.builder()
                        .uid(caracteristicaDto.getUid())
                        .nome(caracteristicaDto.getNome())
                        .icone(caracteristicaDto.getIcone()).build()).collect(Collectors.toList()))
               .imagensLista(produto.getImagensLista().stream().map(imagemDto -> ImagemDto.builder()
                        .uid(imagemDto.getUid())
                        .titulo(imagemDto.getTitulo())
                        .url(imagemDto.getUrl()).build()).collect(Collectors.toList()))
               .imagens(produto.getImagens().stream().map(reference -> FileReference.builder()
                        .id(reference.getId())
                        .dataCriacao(reference.getDataCriacao())
                        .nome(reference.getNome())
                        .contentType(reference.getContentType())
                        .contentLength(reference.getContentLength())
                        .temp(false)
                        .type(reference.getType())
                        .downloadUrl(reference.getDownloadUrl()).build()).collect(Collectors.toList()))
               .categoria(CategoriaDto.builder().uid(produto.getCategoria().getUid())
                        .descricao(produto.getCategoria().getDescricao())
                        .urlImagem((produto.getCategoria().getUrlImagem())).build())
               .cidade(CidadeDto.builder().uid(produto.getCidade().getUid())
                        .nome(produto.getCidade().getNome())
                        .pais(produto.getCidade().getPais()).build())
               .regras(produto.getRegras())
               .saudeSeguranca(produto.getSaudeSeguranca())
               .politicaCancelamento(produto.getPoliticaCancelamento())
               .build();
   }

}
