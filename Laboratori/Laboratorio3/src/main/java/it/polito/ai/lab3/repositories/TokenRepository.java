package it.polito.ai.lab3.repositories;

import it.polito.ai.lab3.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;


public interface TokenRepository extends JpaRepository<Token, String> {

    List<Token> findAllByExpirateDateBefore(Timestamp time); // Per selezionare quelli scaduti

    List<Token> findAllByTeamId(Long teamId); // Per selezionare quelli legati al team

}
