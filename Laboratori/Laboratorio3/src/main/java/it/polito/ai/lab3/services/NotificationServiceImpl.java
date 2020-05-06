package it.polito.ai.lab3.services;

import it.polito.ai.lab3.dtos.TeamDTO;
import it.polito.ai.lab3.entities.Token;
import it.polito.ai.lab3.repositories.TokenRepository;
import it.polito.ai.lab3.services.exceptions.TokenNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    TeamServices teamServices;


    @Override
    public void sendMessage(String address, String subject, String body) {

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(address);
            message.setSubject(subject);
            message.setText(body );
            javaMailSender.send(message);
    }

    @Override
    public boolean confirm(String token) {

        if(!tokenRepository.existsById(token)) {
            throw new TokenNotFoundException("Token inesistente.");
        }

        Token t = tokenRepository.getOne(token);
        if ( t.getExpirateDate().before( Timestamp.valueOf(LocalDateTime.now()) ) ){
            return false;
        }

        if (tokenRepository.findAllByTeamId(t.getTeamId()).size()==1){

            teamServices.setTeamStatus(t.getTeamId());
            tokenRepository.delete(t);
            return true;
        }

        tokenRepository.delete(t);
        return false;
    }

    @Override
    public boolean reject(String token) {

        if(!tokenRepository.existsById(token)) {
            throw new TokenNotFoundException("Token inesistente.");
        }

        Token t = tokenRepository.getOne(token);
        if ( t.getExpirateDate().before( Timestamp.valueOf(LocalDateTime.now()) ) ){
            return false;
        }

        tokenRepository.findAllByTeamId(t.getTeamId()).stream()
                .forEach(tok -> tokenRepository.delete(tok));
        teamServices.evictTeam(t.getTeamId());
        return true;
    }

    @Override
    public void notifyTeam(TeamDTO dto, List<String> memeberIds) {
        for(String id : memeberIds){

            String randomToken = UUID.randomUUID().toString();
            Token t = new Token();
            t.setTeamId(dto.getId());
            t.setId(randomToken);
            t.setExpirateDate(Timestamp.valueOf(LocalDateTime.now().plusHours(1)));
            tokenRepository.save(t);



            String email = "Ciao, \n" +
                    "Se intendi confermare la partecipazione al team "+dto.getName()+", " +
                    "clicca sul link seguente: \n" +
                    " http://localhost:8080/notification/confirm/"+randomToken+" \n\n" +
                    "Per rifiutare premi sul link: \n" +
                    "http://localhost:8080/notification/reject/"+randomToken+" \n";

            sendMessage("s265353@studenti.polito.it", "Team proposal", email);


        }

    }
}
