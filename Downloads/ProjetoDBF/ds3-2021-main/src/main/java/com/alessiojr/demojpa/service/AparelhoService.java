package com.alessiojr.demojpa.service;

import com.alessiojr.demojpa.domain.Aparelho;
import com.alessiojr.demojpa.repository.AparelhoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AparelhoService {
    private final Logger log = LoggerFactory.getLogger(AparelhoService.class);

    private final AparelhoRepository aparelhoRepository;

    public AparelhoService(AparelhoRepository clinicaRepository) {
        this.aparelhoRepository = clinicaRepository;
    }

    public List<Aparelho> findAllList(){
        log.debug("Request to get All Aparelho");
        return aparelhoRepository.findAll();
    }

    public Optional<Aparelho> findOne(Long id) {
        log.debug("Request to get Aparelho : {}", id);
        return aparelhoRepository.findById(id);
    }

    public void delete(Long id) {
        log.debug("Request to delete Aparelho : {}", id);
        aparelhoRepository.deleteById(id);
    }

    public Aparelho save(Aparelho aparelho) {
        log.debug("Request to save Aparelho : {}", aparelho);
        aparelho = aparelhoRepository.save(aparelho);
        return aparelho;
    }

    public List<Aparelho> saveAll(List<Aparelho> aparelhos) {
        log.debug("Request to save Aparelho : {}", aparelhos);
        aparelhos = aparelhoRepository.saveAll(aparelhos);
        return aparelhos;
    }
}
