package com.alessiojr.demojpa.repository;

import com.alessiojr.demojpa.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
}
