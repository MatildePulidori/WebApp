package it.polito.ai.security;

import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Date;

public class HomeController {


    @GetMapping("/")
    MyData home(Principal principal) {
        return MyData.builder()
                .name(principal!=null? principal.getName(): "<NO NAME>")

                .date(new Date())
                .build();
    }
}
