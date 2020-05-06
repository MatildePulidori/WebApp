package it.polito.ai.lab3;

import it.polito.ai.lab3.entities.User;
import it.polito.ai.lab3.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Slf4j
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    UserRepository users;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        log.debug("Initializing users details...");

        log.debug("Printing all users...");
        this.users.findAll().forEach(v -> log.debug(" Users :" + v.toString()));
/*
        this.users.save(User.builder()
                .username("studente")
                .password(this.passwordEncoder.encode("studente"))
                .roles(Arrays.asList( "STUDENT"))
                .build()
        );

        this.users.save(User.builder()
                .username("docente")
                .password(this.passwordEncoder.encode("docente"))
                .roles(Arrays.asList("TEACHER"))
                .build()
        );
*/
        log.debug("Printing all users...");
        this.users.findAll().forEach(v -> log.debug(" User :" + v.toString()));

    }
}
