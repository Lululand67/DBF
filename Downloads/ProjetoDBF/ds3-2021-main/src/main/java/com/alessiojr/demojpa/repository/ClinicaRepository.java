package com.alessiojr.demojpa.repository;

import com.alessiojr.demojpa.domain.Clinica;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicaRepository extends JpaRepository<Clinica, Long> {
    
    Optional<Clinica> findByCnpj(String cnpj);

}
