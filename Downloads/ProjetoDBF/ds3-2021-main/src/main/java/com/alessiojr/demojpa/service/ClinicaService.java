package com.alessiojr.demojpa.service;

import com.alessiojr.demojpa.domain.Clinica;
import com.alessiojr.demojpa.repository.ClinicaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ClinicaService {
    private final Logger log = LoggerFactory.getLogger(ClinicaService.class);

    @Autowired
    private ClinicaRepository clinicaRepository;



    public List<Clinica> findAllList(){
        log.debug("Request to get All Clinica");
        return clinicaRepository.findAll();
    }

    public Optional<Clinica> findOne(Long id) {
        log.debug("Request to get Clinica : {}", id);
        return clinicaRepository.findById(id);
    }

    public void delete(Long id) {
        log.debug("Request to delete Clinica : {}", id);
        clinicaRepository.deleteById(id);
    }

    public Clinica save(Clinica clinica) {
        log.debug("Request to save Clinica : {}", clinica);
        clinica = clinicaRepository.save(clinica);
        return clinica;
    }

    public List<Clinica> saveAll(List<Clinica> clinicas) {
        log.debug("Request to save Clinica : {}", clinicas);
        clinicas = clinicaRepository.saveAll(clinicas);
        return clinicas;
    }
    
    public Optional<Clinica> findByCnpj(String cnpj) {
        log.info("Buscando um Usuário para o cnpj {}", cnpj);
        return clinicaRepository.findByCnpj(cnpj);
    }
	
}
