package com.alessiojr.demojpa.web.api;

import com.alessiojr.demojpa.domain.Pedido;
import com.alessiojr.demojpa.service.PedidoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/pedidos")
public class PedidoResource {
    
    private final Logger log = LoggerFactory.getLogger(DentistaResource.class);

    @Autowired
    private PedidoService pedidoService;

    public PedidoResource() {
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> mostrarPedidoPorId(@PathVariable Long id) {
        log.debug("REST request to get Pedido : {}", id);
        Optional<Pedido> pedido = pedidoService.findOne(id);
        if(pedido.isPresent()) {
            return ResponseEntity.ok().body(pedido.get());
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/")
    public ResponseEntity<List<Pedido>> listarPedidos(){
        List<Pedido> lista = pedidoService.findAllList();
        if(lista.size() > 0) {
            return ResponseEntity.ok().body(lista);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/")
    public ResponseEntity<Pedido> atualizarPedido(@RequestBody Pedido pedido) throws URISyntaxException {
        log.debug("REST request to update Pedido : {}", pedido);
        if (pedido.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid Pedido id null");
        }
        Pedido result = pedidoService.save(pedido);
        return ResponseEntity.ok()
                .body(result);
    }

    @PostMapping("/")
    public ResponseEntity<Pedido> adicionarPedido(@RequestBody Pedido pedido) throws URISyntaxException {
        log.debug("REST request to save Pedido : {}", pedido);
        if (pedido.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Um novo pedido n??o pode ter um ID");
        }
        Pedido result = pedidoService.save(pedido);
        return ResponseEntity.created(new URI("/api/pedidos/" + result.getId()))
                .body(result);
    }

    @PostMapping(value = "/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Pedido> baixarListaDePedidos(@RequestPart("data") MultipartFile csv) throws IOException {
        List<Pedido> savedNotes = new ArrayList<>();
        List<Pedido> notes = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(csv).getInputStream(), StandardCharsets.UTF_8)).lines()
                .map(Pedido::parseNote).collect(Collectors.toList());
        pedidoService.saveAll(notes).forEach(savedNotes::add);
        return savedNotes;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPedido(@PathVariable Long id) {
        log.debug("REST request to delete pedido : {}", id);

        pedidoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
