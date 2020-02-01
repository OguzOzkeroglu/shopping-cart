package com.foo.shoppingcart.repository;

import com.foo.shoppingcart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Oguz Ozkeroglu
 * Created on 2020.02.01
 */
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
