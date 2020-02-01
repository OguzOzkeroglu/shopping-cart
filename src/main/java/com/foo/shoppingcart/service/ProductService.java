package com.foo.shoppingcart.service;

import com.foo.shoppingcart.exception.ResourceNotFoundException;
import com.foo.shoppingcart.model.Product;
import com.foo.shoppingcart.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Oguz Ozkeroglu
 * Created on 2020.02.01
 */

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with given id: " + id));
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product update(Long id, Product product) {
        productRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        product.setId(id);
        return productRepository.save(product);
    }

    public void delete(Long id) {
        Product product = productRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        productRepository.delete(product);
    }
}
