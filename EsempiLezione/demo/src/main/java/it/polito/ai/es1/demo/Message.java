package it.polito.ai.es1.demo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
// Queste due annotazioni fanno parta della libreria Lombok, inclusa.
// Lombok serve a potenziare la classe che abbiamo scritto,
// aggiungendo tutto quello che non abbiamo messo, ma che avremmo voluto
// La classe avrà tutti i metodi set, get, toString, hashCode, equals
// e un costruttore con due argomenti (uno di tipo String e uno di LocalDateTime.

public class Message {
    private String text;
    private LocalDateTime time;


}
