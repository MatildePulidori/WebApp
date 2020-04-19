package it.polito.ai.es1.demo;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.time.LocalDateTime;

@RestController

//Cotruisco un'applicazione web capace di rispondere al protocollo REST
// Un'istanza di questa classe sarà dedicata a gestirmi i metodi e il comportamento
// dell'HTTP con le chiamate REST
public class HomeController {

    // GetMapping con lo "/" dice che il nostro messaggio sarà accessibile
    // facendo una richiesta HTTP verso la URL "/"; URL "/" di quale host?
    // Dobbiamo istanziare un server web che sia capace di "servirlo".
    // Una delle tante cose che il sistema di annotazioni permette di fare
    // è quella di riconoscere in automatico che la nostra classe usa un servizio web
    // e di metterlo a disposizione.
    @GetMapping("/")
    public Message home(){
        return new Message("Benvenuti al corso", LocalDateTime.now());
    }


}
