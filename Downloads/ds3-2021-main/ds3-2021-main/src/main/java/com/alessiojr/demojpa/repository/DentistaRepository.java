package com.alessiojr.demojpa.repository;

import com.alessiojr.demojpa.domain.Dentista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DentistaRepository extends JpaRepository<Dentista, Long> {
    
}
