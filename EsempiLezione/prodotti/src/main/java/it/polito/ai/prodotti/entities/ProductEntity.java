package it.polito.ai.prodotti.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "product")
public class ProductEntity {
    @Id
    String id;
    String name;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "product_ingredients",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<IngredientEntity> ingredientEntities = new ArrayList<>();

}

