package com.digitalbooking.digitalbooking.service.impl;

import com.digitalbooking.digitalbooking.domain.Produto;
import com.digitalbooking.digitalbooking.domain.Reserva;
import com.digitalbooking.digitalbooking.domain.Usuario;
import com.digitalbooking.digitalbooking.dto.ClienteDto;
import com.digitalbooking.digitalbooking.dto.ProdutoDto;
import com.digitalbooking.digitalbooking.dto.ReservaDto;
import com.digitalbooking.digitalbooking.repository.ProdutoRepository;
import com.digitalbooking.digitalbooking.repository.ReservaRepository;
import com.digitalbooking.digitalbooking.repository.UsuarioRepository;
import com.digitalbooking.digitalbooking.service.ReservaService;
import com.digitalbooking.digitalbooking.utils.Utils;
import lombok.extern.log4j.Log4j2;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Log4j2
@Service
public class ReservaServiceImpl implements ReservaService {

   @Autowired
   ReservaRepository reservaRepository;
   @Autowired
   ProdutoRepository produtoRepository;
   @Autowired
   UsuarioRepository usuarioRepository;

   @Override
   @Transactional(readOnly = true)
   public ResponseEntity<List<ReservaDto>> findReservaByUsuarioEmail(ClienteDto clienteDto) {
      return ResponseEntity.status(HttpStatus.OK).body(reservaRepository.findByUsuarioEmail(clienteDto.getEmail()).stream().map(reserva -> toMapReservaDto(reserva)).collect(Collectors.toList()));
   }

   @Override
   public ResponseEntity<Optional<ReservaDto>> findReservaByUid(Long uid) {
      try{
         Optional<ReservaDto> reservaDto = reservaRepository.findByUid(uid).map(reserva -> toMapReservaDto(reserva));
         reservaDto.orElseThrow(() -> new ObjectNotFoundException(reservaDto, ReservaDto.class.getName()));
         return ResponseEntity.status(HttpStatus.OK).body(reservaDto);
      }catch(Exception e){
         return new ResponseEntity("Reserva " +uid+" não encontrada.",HttpStatus.NOT_FOUND);
      }
   }

   @Transactional(propagation= Propagation.REQUIRED, noRollbackFor=Exception.class)
   @Override
   public ResponseEntity<ReservaDto> saveReserva(ReservaDto reservaDto) {
      try{
         reservaDto.setUid(new Utils().gerarUid());
         Produto produto = produtoRepository.findByUid(reservaDto.getProduto().getUid()).orElseThrow(() -> new NotFoundException("Produto não encontrado"));
         Usuario usuario = usuarioRepository.findByEmail(reservaDto.getCliente().getEmail()).orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

         Reserva reserva = toMapReserva(reservaDto);
         reserva.setProduto(produto);
         reserva.setUsuario(usuario);
         return ResponseEntity.status(HttpStatus.CREATED).body(toMapReservaDto(reservaRepository.save(reserva)));
      }catch (Exception e){
         log.info("[ReservaService] [saveReserva] Dados obrigatórios faltando: " + reservaDto.toString());
         log.error("[ReservaService] [saveReserva] Erro: " + e);
         return new ResponseEntity("Infelizmente, a reserva não pôde ser completada. Por favor, tente novamente mais tarde ", HttpStatus.BAD_REQUEST);
      }
   }



   private ReservaDto toMapReservaDto(Reserva reserva) {
      return ReservaDto.builder()
               .uid(reserva.getUid())
               .horaInicioReserva(reserva.getHoraInicioReserva())
               .dataInicioReserva(reserva.getDataInicioReserva())
               .dataFinalReserva(reserva.getDataFinalReserva())
               .produto(ProdutoDto.builder()
                        .uid(reserva.getProduto().getUid())
                        .nome(reserva.getProduto().getNome())
                        .descricao(reserva.getProduto().getDescricao())
                        .latitude(reserva.getProduto().getLatitude())
                        .longitude(reserva.getProduto().getLongitude())
                        .qualificacao(reserva.getProduto().getQualificacao())
                        .classificacao(reserva.getProduto().getClassificacao())
                        .build())
               .cliente(ClienteDto.builder()
                        .email(reserva.getUsuario().getEmail())
                        .nome(reserva.getUsuario().getNome())
                        .sobrenome(reserva.getUsuario().getSobrenome())
                        .build())
               .build();
   }
   private Reserva toMapReserva(ReservaDto reservaDto) {
      return Reserva.builder()
               .uid(reservaDto.getUid())
               .horaInicioReserva(reservaDto.getHoraInicioReserva())
               .dataInicioReserva(reservaDto.getDataInicioReserva())
               .dataFinalReserva(reservaDto.getDataFinalReserva())
               .produto(Produto.builder()
                        .uid(reservaDto.getProduto().getUid())
                        .nome(reservaDto.getProduto().getNome())
                        .descricao(reservaDto.getProduto().getDescricao())
                        .latitude(reservaDto.getProduto().getLatitude())
                        .longitude(reservaDto.getProduto().getLongitude())
                        .qualificacao(reservaDto.getProduto().getQualificacao())
                        .classificacao(reservaDto.getProduto().getClassificacao())
                        .build())
               .usuario(Usuario.builder()
                        .email(reservaDto.getCliente().getEmail())
                        .build())
               .build();
   }
}
