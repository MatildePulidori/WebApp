package it.polito.ai.prodotti;

import it.polito.ai.prodotti.entities.IngredientEntity;
import it.polito.ai.prodotti.entities.ProductEntity;
import it.polito.ai.prodotti.repositories.IngredientRepository;
import it.polito.ai.prodotti.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProdottiApplication {

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(ProdottiApplication.class, args);
    }

}
