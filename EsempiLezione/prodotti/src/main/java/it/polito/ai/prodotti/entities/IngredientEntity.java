package it.polito.ai.prodotti.entities;


import lombok.Data;
import org.springframework.context.annotation.Primary;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name= "ingredient")
public class IngredientEntity {
   @Id
   String id;
   String name;
}
