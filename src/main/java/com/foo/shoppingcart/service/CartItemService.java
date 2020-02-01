package com.foo.shoppingcart.service;

import com.foo.shoppingcart.exception.ResourceNotFoundException;
import com.foo.shoppingcart.model.Campaign;
import com.foo.shoppingcart.model.CartItem;
import com.foo.shoppingcart.repository.CampaignRepository;
import com.foo.shoppingcart.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Oguz Ozkeroglu
 * Created on 2020.02.01
 */

@RequiredArgsConstructor
@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;

    public CartItem findById(Long id) {
        return cartItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem not found with given id: " + id));
    }

    public List<CartItem> findAll() {
        return cartItemRepository.findAll();
    }

    public CartItem save(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    public CartItem update(Long id, CartItem cartItem) {
        cartItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem not found with given id: " + id));

        cartItem.setId(id);
        return cartItemRepository.save(cartItem);
    }

    public void delete(Long id) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem not found with given id: " + id));

        cartItemRepository.delete(cartItem);
    }
}
