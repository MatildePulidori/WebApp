package it.polito.ai;

import java.util.Arrays;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        // 8) Stream.of() permette di eliminare la definizione di words,
        // senza cambiare il risultato di esecuzione
        // String[] words = {"Alfa", "Beta", "Gamma", "Delta"};

        // 1) Per convertire il mio array di stringhe in un array di stringhe
        // uso questo metodo, che ha trasformato il mio array words
        // in qualcosa che emette di volta in volta una stringa alla volta


        // 7) Arrays.stream(words) vedi punto 8
        Stream.of("Alfa", "Beta", "Gamma", "Delta")
                //.filter(s->s.length()>3)
                .filter(s -> s.endsWith("ta"))
                // 6) Se faccio precedere a map .parallel()
                // vedo che l'esecuzione è stata fatta da due thread diversi
                .parallel()
                .map(s -> {
                     System.out.println(Thread.currentThread());
                    return s;
                })
                // 5) Questa non è una grande idea, esamino tutti i dati che mi arrivano: c'è un effetto collaterale
                // Non è una grande idea: il presupposto degli stream è che le funzioni che andiamo a utilizzare
                // siano funzioni pure, ovvero senza effetti collaterali
                // Ci aiuta solo a capire che cosa sta succedendo
                .map(String::toUpperCase)
                .forEach(System.out::println);


        // 2) Voglio trasformare queste stringhe in qualcos'altro:
        // posso usare la funzione filter() per selezionarne solo alcune
        // quelle che soddisfano la mia funzione lambda

        // 3) Voglio trasformarle tutte in maiuscolo : sui singoli elementi
        // dello stream applico la funzione istanza toUpperCase che trasforma gli elementi
        // restanti della funzione filtro in UpperCase

        // 4) poi le stampo con System.out::println

        System.out.println();

        // 9) uso di iterate()
        Stream.iterate(1, i -> i + 1)
                .limit(10)
                // limit è un operatore intermedio che dice:
                // dopo che sono transitati n elementi nel flusso, interrompilo
                .forEach(System.out::println);

        System.out.println();

        // 10) uso di generate()
        Stream.generate(()->Math.random())
                .limit(10)
                .forEach(System.out::println);

        System.out.println();

        // 11) uso di concat()
        Stream<Double> s1 =
                Stream.generate(()->Math.random()).limit(10);
        Stream<Double> s2 =
                Stream.iterate(1.0, d->d+0.25).limit(10);
        // Stream.concat(s1, s2).forEach(System.out::println);

        System.out.println();

        // 12) metodo reduce()
        System.out.println(s2.reduce("", (a,b)->a+" "+b, (a,b)->a+" "+b));
    }


}
