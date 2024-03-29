package br.com.ibmec.cloud.Spotify2.models;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    @Column
    private UUID id;

    @Column
    private String nome;

    @OneToMany
    private List<Musicas> Musicas;

}
