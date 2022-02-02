package com.alessiojr.demojpa.repository;

import com.alessiojr.demojpa.domain.Secretaria;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecretariaRepository extends JpaRepository<Secretaria, Long>{
    Optional<Secretaria> findByEmail(String email);
    Optional<Secretaria> findUsuarioByEmailAndSenha(String email, String senha);
}
