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
@Table(name = "table_secretaria")

public class Secretaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nome", length = 64)
    private String nome;

    
    @Column(unique = true)
    private String email;
    
    private String senha;


    
    
    private Boolean isActive;

    public static Secretaria parseNote(String line) {
        String[] text = line.split(",");
        Secretaria note = new Secretaria();
        note.setId(Long.parseLong(text[0]));
        note.setNome(text[1]);
        return note;
    }
}
