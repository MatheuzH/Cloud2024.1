package br.com.ibmec.cloud.Spotify2.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Plano {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    @NotBlank(message = "campo nome é obrigatório ")
    private String nome;

    @Column
    @NotBlank (message = "campo preço é obrigatório ")
    private double preco;




}
