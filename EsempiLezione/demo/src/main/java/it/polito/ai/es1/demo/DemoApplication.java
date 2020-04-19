package it.polito.ai.es1.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Primo esempio di annotazione:
// rendono compatta la quantità di codice che si scrive
// e permettono di arricchire le classi e di
// generare in automatico una grande quantità di codice
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {

        SpringApplication.run(DemoApplication.class, args);

        // Se mandiamo il main,
        // "Tomcat initialized with port(s): 8080 (http)" vuol dire che
        // il  sistema si è "accorto" che avevamo bisogno di un servizio web
        // e lo ha aperto.
        }

}
