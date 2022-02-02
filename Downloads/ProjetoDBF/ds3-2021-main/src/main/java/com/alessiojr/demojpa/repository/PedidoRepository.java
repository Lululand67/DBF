package com.alessiojr.demojpa.repository;

import com.alessiojr.demojpa.domain.Aparelho;
import com.alessiojr.demojpa.domain.Dentista;
import com.alessiojr.demojpa.domain.Pedido;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    Optional<Dentista> findByName(String nome);
    Optional<Aparelho> findByNameA(String nome);
}

