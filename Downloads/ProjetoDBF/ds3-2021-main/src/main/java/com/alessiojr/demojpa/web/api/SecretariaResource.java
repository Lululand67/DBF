package com.alessiojr.demojpa.web.api;

import com.alessiojr.demojpa.domain.Secretaria;
import com.alessiojr.demojpa.service.SecretariaService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.annotations.Api;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
//import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/secretarias")
@Api(value = "secretaria", tags = "ProLab Duarte")
@CrossOrigin(origins = "*")
public class SecretariaResource {
    
    private final Logger log = LoggerFactory.getLogger(SecretariaResource.class);

    @Autowired
	private SecretariaService secretariaService;
    
	//@Autowired
	//private PasswordEncoder encoder;
    public SecretariaResource() {
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
        return ResponseEntity.ok().body(lista);
       
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
    public ResponseEntity<Secretaria> adicionarSecretaria( @RequestBody Secretaria secretaria) throws URISyntaxException {
        log.debug("REST request to save Secretaria : {}", secretaria);
        if (secretaria.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Um novo dentista não pode ter um ID");
        }
        Secretaria result = secretariaService.save(secretaria);
        return ResponseEntity.created(new URI("/api/secretarias/" + result.getId()))
                .body(result);
    }
    @PutMapping("/updatePassword")
    public ResponseEntity<Secretaria> saveAndUpdatePassword( @RequestBody Secretaria secretaria) throws URISyntaxException {
        log.debug("REST request to update password : {}", secretaria);
        if (secretaria.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid secretaria id null");
        }
        /*implementação do spring security */     
        Secretaria result = secretariaService.save(secretaria);
        return ResponseEntity.ok()
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
    @GetMapping("/{email}/exists")
    public ResponseEntity<Boolean> isExisting(@PathVariable String email){
        log.info("REST request to get usuario By email : {}", email);
        if(secretariaService.findByEmail(email).isPresent()) {
            return ResponseEntity.ok().body(Boolean.TRUE);
        }else{
        	return ResponseEntity.ok().body(Boolean.FALSE);
        }
    }
    @GetMapping("/{email}/{senha}/authenticate")
    public ResponseEntity<Secretaria> authenticateSecretaria(@PathVariable  String email, @PathVariable String senha){
        log.debug("REST request to registrar secretaria Secretaria : {}", email, senha);
        /*implementação do spring security */
       
        Optional<Secretaria> secretaria = secretariaService.findUsuarioByEmailAndSenha(email, senha);

        if(secretaria.isPresent()) {
            return ResponseEntity.ok().body(secretaria.get());
        }else{
        	return ResponseEntity.ok().body(new Secretaria());
        }

    }
    
    
    @GetMapping("/{email}/authenticate")
    public ResponseEntity<Secretaria> getSecretaria(@PathVariable String email) {
        log.info("REST request to get usuario by email : {}", email);
        Optional<Secretaria> secretaria = secretariaService.findByEmail(email);
        if(secretaria.isPresent()) {
            return ResponseEntity.ok().body(secretaria.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
