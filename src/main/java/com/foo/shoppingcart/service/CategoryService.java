package com.foo.shoppingcart.service;

import com.foo.shoppingcart.exception.ResourceNotFoundException;
import com.foo.shoppingcart.model.Category;
import com.foo.shoppingcart.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Oguz Ozkeroglu
 * Created on 2020.02.01
 */

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with given id: " + id));
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Category update(Long id, Category category) {
        categoryRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        category.setId(id);
        return categoryRepository.save(category);
    }

    public void delete(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        categoryRepository.delete(category);
    }
}
