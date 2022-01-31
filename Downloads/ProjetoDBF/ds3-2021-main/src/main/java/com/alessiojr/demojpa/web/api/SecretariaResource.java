package com.alessiojr.demojpa.web.api;

import com.alessiojr.demojpa.domain.Secretaria;
import com.alessiojr.demojpa.service.SecretariaService;
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

@RestController
@RequestMapping("/secretarias")
public class SecretariaResource {
    
    private final Logger log = LoggerFactory.getLogger(SecretariaResource.class);

    private final SecretariaService secretariaService;

    public SecretariaResource(SecretariaService secretariaService) {
        this.secretariaService = secretariaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Secretaria> mostrarSecretariaPorId(@PathVariable Long id) {
        log.debug("REST request to get Secretaria : {}", id);
        Optional<Secretaria> secretaria = secretariaService.findOne(id);
        if(secretaria.isPresent()) {
            return ResponseEntity.ok().body(secretaria.get());
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/")
    public ResponseEntity<List<Secretaria>> listarSecretarias(){
        List<Secretaria> lista = secretariaService.findAllList();
        if(lista.size() > 0) {
            return ResponseEntity.ok().body(lista);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/")
    public ResponseEntity<Secretaria> atualizarSecretaria(@RequestBody Secretaria dentista) throws URISyntaxException {
        log.debug("REST request to update Secretaria : {}", dentista);
        if (dentista.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid Secretaria id null");
        }
        Secretaria result = secretariaService.save(dentista);
        return ResponseEntity.ok()
                .body(result);
    }

    @PostMapping("/")
    public ResponseEntity<Secretaria> adicionarSecretaria(@RequestBody Secretaria secretaria) throws URISyntaxException {
        log.debug("REST request to save Secretaria : {}", secretaria);
        if (secretaria.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Um novo dentista não pode ter um ID");
        }
        Secretaria result = secretariaService.save(secretaria);
        return ResponseEntity.created(new URI("/api/secretarias/" + result.getId()))
                .body(result);
    }

    @PostMapping(value = "/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Secretaria> baixarListaDeSecretarias(@RequestPart("data") MultipartFile csv) throws IOException {
        List<Secretaria> savedNotes = new ArrayList<>();
        List<Secretaria> notes = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(csv).getInputStream(), StandardCharsets.UTF_8)).lines()
                .map(Secretaria::parseNote).collect(Collectors.toList());
        secretariaService.saveAll(notes).forEach(savedNotes::add);
        return savedNotes;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirSecretaria(@PathVariable Long id) {
        log.debug("REST request to delete dentista : {}", id);

        secretariaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
