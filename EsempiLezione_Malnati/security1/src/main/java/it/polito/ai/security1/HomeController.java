package it.polito.ai.security1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;
import java.security.Principal;

@RestController
public class HomeController {

    @GetMapping("/")
    MyData home(Principal principal){
        return MyData.builder()
                .name(principal!=null? principal.getName(): "<NO_NAME>")
                .date(new Date())
                .build();

    }
}
