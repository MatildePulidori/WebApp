package it.polito.ai.prodotti.services;

import it.polito.ai.prodotti.dtos.IngredientDTO;
import it.polito.ai.prodotti.dtos.ProductDTO;
import it.polito.ai.prodotti.entities.IngredientEntity;
import it.polito.ai.prodotti.entities.ProductEntity;
import it.polito.ai.prodotti.repositories.IngredientRepository;
import it.polito.ai.prodotti.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {


    @Autowired
    ProductRepository productRepository;

    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<ProductDTO> getProducts() {
        return productRepository.findAll()
                .stream()
                .map(p -> modelMapper.map(p, ProductDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProduct(String id) {
        ProductEntity p = productRepository.getOne(id);
        if (p == null) return null;
        return modelMapper.map(p, ProductDTO.class);
    }

    @Override
    public List<IngredientDTO> getIngredients(String id) {
        ProductEntity p = productRepository.getOne(id);
        if (p == null) return null;
        return p.getIngredientEntities()
                .stream()
                .map(i -> modelMapper.map(i, IngredientDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getProductsByIngredient(String ingredientId) {
        IngredientEntity ingredientEntity = ingredientRepository.getOne(ingredientId);
        if (ingredientEntity == null) return null;
        return productRepository.getByIngredientEntitiesContaining(ingredientEntity)
                .stream()
                .map(p -> modelMapper.map(p, ProductDTO.class))
                .collect(Collectors.toList());
    }


}
