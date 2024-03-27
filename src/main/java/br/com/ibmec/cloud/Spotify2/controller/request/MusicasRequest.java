package br.com.ibmec.cloud.Spotify2.controller.request;

import lombok.Data;

import java.util.UUID;

@Data
public class MusicasRequest {
    private UUID id;
    private String nome;
    private int duracao;
}
