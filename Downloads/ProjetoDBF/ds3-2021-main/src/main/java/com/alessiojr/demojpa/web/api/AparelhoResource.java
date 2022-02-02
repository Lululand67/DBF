package com.alessiojr.demojpa.web.api;

import com.alessiojr.demojpa.domain.Aparelho;
import com.alessiojr.demojpa.service.AparelhoService;
import io.swagger.annotations.Api;
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
@RequestMapping("/aparelhos")
@Api(value = "aparelho", tags = "ProLab Duarte")
@CrossOrigin(origins = "*")
public class AparelhoResource {
    
    private final Logger log = LoggerFactory.getLogger(AparelhoResource.class);

    @Autowired
    private AparelhoService aparelhoService;
    
    public AparelhoResource() {
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aparelho> mostrarAparelhoPorId(@PathVariable Long id) {
        log.debug("REST request to get Aparelho : {}", id);
        Optional<Aparelho> aparelho = aparelhoService.findOne(id);
        if(aparelho.isPresent()) {
            return ResponseEntity.ok().body(aparelho.get());
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/")
    public ResponseEntity<List<Aparelho>> listarAparelhos(){
        List<Aparelho> lista = aparelhoService.findAllList();
        if(lista.size() > 0) {
            return ResponseEntity.ok().body(lista);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/")
    public ResponseEntity<Aparelho> AtualizarAparelho(@RequestBody Aparelho aparelho) throws URISyntaxException {
        log.debug("REST request to update Aparelho : {}", aparelho);
        if (aparelho.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid Aparelho id null");
        }
        Aparelho result = aparelhoService.save(aparelho);
        return ResponseEntity.ok()
                .body(result);
    }

    @PostMapping("/")
    public ResponseEntity<Aparelho> AdicionarAparelho(@RequestBody Aparelho aparelho) throws URISyntaxException {
        log.debug("REST request to save Aparelho : {}", aparelho);
        if (aparelho.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Uma nova aparelho não pode ter um ID");
        }
        Aparelho result = aparelhoService.save(aparelho);
        return ResponseEntity.created(new URI("/api/aparelhos/" + result.getId()))
                .body(result);
    }

    @PostMapping(value = "/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Aparelho> baixarListaDeAparelho(@RequestPart("data") MultipartFile csv) throws IOException {
        List<Aparelho> savedNotes = new ArrayList<>();
        List<Aparelho> notes = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(csv).getInputStream(), StandardCharsets.UTF_8)).lines()
                .map(Aparelho::parseNote).collect(Collectors.toList());
        aparelhoService.saveAll(notes).forEach(savedNotes::add);
        return savedNotes;
    }

   @DeleteMapping("/{id}")
    public ResponseEntity<Void> ExcluirAparelho(@PathVariable Long id) {
        log.debug("REST request to delete Aparelho : {}", id);

        aparelhoService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{nome}/exists")
    public ResponseEntity<Boolean> isExisting(@PathVariable String nome){
        log.info("REST request to get Categoria By Descrição : {}", nome);

        if(aparelhoService.findByNome(nome).isPresent()) {
            return ResponseEntity.ok().body(Boolean.TRUE);
        }else{
        	return ResponseEntity.ok().body(Boolean.FALSE);
        }
    }
}
