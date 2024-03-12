package br.com.ibmec.cloud.Spotify2.repositor;


import br.com.ibmec.cloud.Spotify2.models.Musicas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MusicasRepository extends JpaRepository<Musicas, UUID> {
}
