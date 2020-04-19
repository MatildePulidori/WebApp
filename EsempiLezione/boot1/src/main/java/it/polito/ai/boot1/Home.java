package it.polito.ai.boot1;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
// I metodi di questa classe restituiscono una stringa e un modello associato
public class Home {

    @GetMapping("/index.html")
    public String index(Model m){
        System.out.println("----------> index");
        m.addAttribute("date", new Date());
        return "home";
    }


}


    /**
    //@RestController
    //@Autowired
    private TimestampService timestampService;
    // Non c'è ancora qualcuno che implementi TimeStampService e che sia visibile a Spring
    // Che fare? Annotazione @Service in TimestampServiceimplementation
    // La classe Home quindi usa il servizio conoscendolo solo come interfaccia, non come implementazione
    // questo permette, all'occorrenza, di sostituire la mia TimestampService implementation,
    // sostituirla con qualcos'altro, ma non dover modificare mille cose del codice, perchè si va a
    // richiamare solo l'interfaccia
    // Autowired --> Guarda tra tutte le classi che Spring conosce, se ce ne sia una
    // che abbia due caratteristiche: 1) Implementi l'interfaccia chiamata, 2) Sia nota a spring,
    // ovvero sia preceduta da un'annotazione del tipo @Component (Service è un component).
    // Spring ne crea un'istanza (singleton), e se qualcuno ne avrà bisogno,
    // (Spring lo sa con Autowired), Spring provvede ad iniettare lì dentro (in questo caso dentro il
    // campo privato TimestampService) il pezzo che serve.


    private List<String> data;


    @Autowired
    public Home(TimestampService timestampService, List<String> data){
        this.timestampService= timestampService;
        this.data = data;
    }

    @GetMapping("/")
    public String home() {
        return "Home page generated at "+timestampService.getTimestamp()+" "+
                data.stream().collect(Collectors.joining(" - "));
    }**/
