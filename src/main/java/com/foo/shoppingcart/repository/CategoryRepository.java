package com.foo.shoppingcart.repository;

import com.foo.shoppingcart.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Oguz Ozkeroglu
 * Created on 2020.02.01
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
