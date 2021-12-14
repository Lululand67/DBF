package com.alessiojr.demojpa.domain;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "table_aparelho")

public class Aparelho {
        @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nome", length = 64)
    private String nome;

    private String descricao;
    private String valor;
    
    @ManyToOne
    private Dentista dentista;
        
    private Boolean isActive;

    public static Aparelho parseNote(String line) {
        String[] text = line.split(",");
        Aparelho note = new Aparelho();
        note.setId(Long.parseLong(text[0]));
        note.setNome(text[1]);
        return note;
    }
}
