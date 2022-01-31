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
@Table(name = "table_dentista")

public class Dentista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nome", length = 64)
    private String nome;

    private Instant dataNascimento;
    
    @Column(unique = true)
    private String cpf;
    
    private String email;
    private String celular;
    
    
    @ManyToOne
    private Clinica clinica;
    
    private Boolean isActive;

    public static Dentista parseNote(String line) {
        String[] text = line.split(",");
        Dentista note = new Dentista();
        note.setId(Long.parseLong(text[0]));
        note.setNome(text[1]);
        return note;
    }
}
