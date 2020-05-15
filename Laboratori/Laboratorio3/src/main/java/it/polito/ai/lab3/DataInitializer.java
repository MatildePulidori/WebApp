package it.polito.ai.lab3;

import it.polito.ai.lab3.entities.User;
import it.polito.ai.lab3.repositories.UserRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Log(topic="Data Initializer")
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    UserRepository users;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        log.info("Initializing users details...");

        log.info("Printing all users...");
        this.users.findAll().forEach(v -> log.info(" Users :" + v.toString()));

        this.users.save(User.builder()
                .username("293847")
                .password(this.passwordEncoder.encode("studente"))
                .roles(Arrays.asList( "ROLE_STUDENT"))
                .build()
        );

        this.users.save(User.builder()
                .username("123456")
                .password(this.passwordEncoder.encode("docente"))
                .roles(Arrays.asList("ROLE_TEACHER"))
                .build()
        );

        log.info("Printing all users...");
        this.users.findAll().forEach(v -> log.info(" User :" + v.toString()));

    }
}
