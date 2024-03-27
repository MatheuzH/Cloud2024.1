package br.com.ibmec.cloud.Spotify2.controller;

import br.com.ibmec.cloud.Spotify2.controller.request.BandaRequest;
import br.com.ibmec.cloud.Spotify2.controller.request.MusicasRequest;
import br.com.ibmec.cloud.Spotify2.models.Banda;
import br.com.ibmec.cloud.Spotify2.models.Musicas;
import br.com.ibmec.cloud.Spotify2.repositor.BandaRepository;
import br.com.ibmec.cloud.Spotify2.repositor.MusicasRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/banda")
public class BandaController {

    @Autowired
    private BandaRepository repository;

    @Autowired
    private MusicasRepository musicasRepository;

    @PostMapping
    public ResponseEntity<Banda> criar(@Valid @RequestBody BandaRequest request) {

        Banda banda = new Banda();
        banda.setNome(request.getNome());
        banda.setDescricao(request.getDescricao());
        banda.setImagem(request.getImagem());

        //Salva no banco de dados da banda
        this.repository.save(banda);

        //Verificar se o usuário enviou musicas
        for (MusicasRequest item : request.getMusicas()) {
            Musicas musicas = new Musicas();
            musicas.setId(UUID.randomUUID());
            musicas.setNome(item.getNome());
            musicas.setDuracao(item.getDuracao());

            //Associa a banda a musica
            banda.getMusicas().add(musicas);

            //Salva no banco de dados
            this.musicasRepository.save(musicas);
        }


        return new ResponseEntity<>(banda, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/adicionarMusicas")
    public ResponseEntity<Banda> adicionarMusicas(@PathVariable("id") UUID id, @Valid @RequestBody List<MusicasRequest> musicasRequestList) {
        // Verifica se a banda existe no banco de dados
        Optional<Banda> optBanda = this.repository.findById(id);
        if (optBanda.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Banda banda = optBanda.get();

        // Cria e associa as novas músicas à banda
        List<Musicas> novasMusicas = new ArrayList<>();
        for (MusicasRequest musicasRequest : musicasRequestList) {
            Musicas musicas = new Musicas();
            musicas.setId(UUID.randomUUID());
            musicas.setNome(musicasRequest.getNome());
            musicas.setDuracao(musicasRequest.getDuracao());
            musicas.setBanda(banda); // Associa a música à banda
            novasMusicas.add(musicas);
        }

        // Adiciona as novas músicas à lista de músicas da banda
        banda.getMusicas().addAll(novasMusicas);

        // Salva as músicas associadas à banda no banco de dados
        this.musicasRepository.saveAll(novasMusicas);

        // Atualiza a banda no banco de dados para refletir as novas músicas adicionadas
        repository.save(banda);

        // Responde para o usuário
        return new ResponseEntity<>(banda, HttpStatus.CREATED);
    }


    @GetMapping("{id}")
    public ResponseEntity<Banda> obter(@PathVariable("id") UUID id) {
        return this.repository.findById(id).map(item -> {
            return new ResponseEntity<>(item, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Banda>> obterTodos(){
        List<Banda> bandas = this.repository.findAll();
        return ResponseEntity.ok(bandas);
    }






}
