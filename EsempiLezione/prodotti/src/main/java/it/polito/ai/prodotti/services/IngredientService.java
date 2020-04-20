package it.polito.ai.prodotti.services;

import it.polito.ai.prodotti.dtos.IngredientDTO;
import it.polito.ai.prodotti.dtos.ProductDTO;

import java.util.List;

public interface IngredientService {

    List<IngredientDTO> getIngredients();

    IngredientDTO getIngredient(String id);

    List<ProductDTO> getProductUsing(String id);
}
