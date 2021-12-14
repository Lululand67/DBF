package com.alessiojr.demojpa.repository;


import com.alessiojr.demojpa.domain.Aparelho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AparelhoRepository extends JpaRepository<Aparelho, Long> {
    
}
