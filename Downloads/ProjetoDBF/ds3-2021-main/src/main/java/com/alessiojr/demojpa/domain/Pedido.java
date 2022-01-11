package com.alessiojr.demojpa.domain;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "table_pedido")

public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Column(name = "nomeP", length = 64)
    private String nomePaciente;
    
    @NotNull
    @Column(name = "nomeD", length = 64)
    private String nomeDentista;
    
    @ManyToOne
    private Dentista dentista;
    
    @ManyToOne
    private Aparelho aparelho;
    
    private String descricao;
    
    @Temporal(TemporalType.DATE)
    private Date dataEmissao;
    
    @Temporal(TemporalType.DATE)
    private Date dataEntrega;
    
    private Float preco;
    
    private Boolean isActive;

    public static Pedido parseNote(String line) {
        String[] text = line.split(",");
        Pedido note = new Pedido();
        note.setId(Long.parseLong(text[0]));
       // note.setNomePaciente(text[1]);
       // note.setNomeDentista(text[1]);
        return note;
    }
}
