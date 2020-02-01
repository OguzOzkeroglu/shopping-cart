package com.foo.shoppingcart.service;

import com.foo.shoppingcart.exception.ResourceNotFoundException;
import com.foo.shoppingcart.model.Cart;
import com.foo.shoppingcart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Oguz Ozkeroglu
 * Created on 2020.02.01
 */

@RequiredArgsConstructor
@Service
public class CartService {
    private final CartRepository cartRepository;

    public Cart findById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with given id: " + id));
    }

    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart update(Long id, Cart cart) {
        cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with given id: " + id));

        cart.setId(id);
        return cartRepository.save(cart);
    }

    public void delete(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with given id: " + id));

        cartRepository.delete(cart);
    }
}
