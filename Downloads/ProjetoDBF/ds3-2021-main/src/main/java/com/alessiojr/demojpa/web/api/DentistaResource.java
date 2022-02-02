package com.alessiojr.demojpa.web.api;

import com.alessiojr.demojpa.domain.Dentista;
import com.alessiojr.demojpa.service.DentistaService;
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
@RequestMapping("/dentistas")
public class DentistaResource {
    
    private final Logger log = LoggerFactory.getLogger(DentistaResource.class);

    @Autowired
    private DentistaService dentistaService;

    public DentistaResource() {
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dentista> mostrarDentistaPorId(@PathVariable Long id) {
        log.debug("REST request to get Dentista : {}", id);
        Optional<Dentista> dentista = dentistaService.findOne(id);
        if(dentista.isPresent()) {
            return ResponseEntity.ok().body(dentista.get());
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/")
    public ResponseEntity<List<Dentista>> listarDentistas(){
        List<Dentista> lista = dentistaService.findAllList();
        if(lista.size() > 0) {
            return ResponseEntity.ok().body(lista);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/")
    public ResponseEntity<Dentista> atualizarDentista(@RequestBody Dentista dentista) throws URISyntaxException {
        log.debug("REST request to update Dentista : {}", dentista);
        if (dentista.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid Dentista id null");
        }
        Dentista result = dentistaService.save(dentista);
        return ResponseEntity.ok()
                .body(result);
    }

    @PostMapping("/")
    public ResponseEntity<Dentista> adicionarDentista(@RequestBody Dentista dentista) throws URISyntaxException {
        log.debug("REST request to save Dentista : {}", dentista);
        if (dentista.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Um novo dentista não pode ter um ID");
        }
        Dentista result = dentistaService.save(dentista);
        return ResponseEntity.created(new URI("/api/dentistas/" + result.getId()))
                .body(result);
    }

    @PostMapping(value = "/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Dentista> baixarListaDeDentistas(@RequestPart("data") MultipartFile csv) throws IOException {
        List<Dentista> savedNotes = new ArrayList<>();
        List<Dentista> notes = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(csv).getInputStream(), StandardCharsets.UTF_8)).lines()
                .map(Dentista::parseNote).collect(Collectors.toList());
        dentistaService.saveAll(notes).forEach(savedNotes::add);
        return savedNotes;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirDentista(@PathVariable Long id) {
        log.debug("REST request to delete dentista : {}", id);

        dentistaService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
     @GetMapping("/{cpf}/exists")
    public ResponseEntity<Boolean> isExisting(@PathVariable String cpf){
        log.info("REST request to get Contato By Cpf : {}", cpf);

        if(dentistaService.findByCpf(cpf).isPresent()) {
            return ResponseEntity.ok().body(Boolean.TRUE);
        }else{
            return ResponseEntity.noContent().build();
        }
    }
}
