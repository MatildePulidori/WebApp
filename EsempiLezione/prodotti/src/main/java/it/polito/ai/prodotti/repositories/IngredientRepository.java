package it.polito.ai.prodotti.repositories;

import it.polito.ai.prodotti.entities.IngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<IngredientEntity, String> {


}
