package com.alessiojr.demojpa.service;

import com.alessiojr.demojpa.domain.Secretaria;
import com.alessiojr.demojpa.repository.SecretariaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class SecretariaService {
    private final Logger log = LoggerFactory.getLogger(SecretariaService.class);

    @Autowired
    private SecretariaRepository secretariaRepository;


    public List<Secretaria> findAllList(){
        log.debug("Request to get All Secretaria");
        return secretariaRepository.findAll();
    }

    public Optional<Secretaria> findOne(Long id) {
        log.debug("Request to get Secretaria : {}", id);
        return secretariaRepository.findById(id);
    }

    public void delete(Long id) {
        log.debug("Request to delete Secretaria : {}", id);
        secretariaRepository.deleteById(id);
    }

    public Secretaria save(Secretaria secretaria) {
        log.debug("Request to save Secretaria : {}", secretaria);
        secretaria = secretariaRepository.save(secretaria);
        return secretaria;
    }

    public List<Secretaria> saveAll(List<Secretaria> secretarias) {
        log.debug("Request to save Secretaria : {}", secretarias);
        secretarias = secretariaRepository.saveAll(secretarias);
        return secretarias;
    }
    
    public Optional<Secretaria> findByEmail(String email) {
		log.info("Buscando um Usuário para o email {}", email);
		return secretariaRepository.findByEmail(email);
	}
	
    public Optional<Secretaria> findUsuarioByEmailAndSenha(String email, String senha) {
        log.debug("Buscando um usuário por Email and Senha : {}", email, senha);
        return secretariaRepository.findUsuarioByEmailAndSenha(email, senha);
    }
}
