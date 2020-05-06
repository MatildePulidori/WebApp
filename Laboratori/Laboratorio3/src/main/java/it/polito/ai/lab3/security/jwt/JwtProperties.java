package it.polito.ai.lab3.security.jwt;

import lombok.Data;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
public class JwtProperties {

    private String secreKey= "secret";

    private long validityInMis = 3600000; // 1h

}
