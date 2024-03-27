package br.com.ibmec.cloud.Spotify2.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class BandaRequest {

    private UUID id;

    @NotBlank(message = "Campo nome é obrigatório")
    private String nome;

    @NotBlank(message = "Campo descrição e obrigatório")
    private String descricao;

    private String imagem;

    private List<MusicasRequest> musicas ;
}
