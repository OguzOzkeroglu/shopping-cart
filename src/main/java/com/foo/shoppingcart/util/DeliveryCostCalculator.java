package com.foo.shoppingcart.util;

import com.foo.shoppingcart.model.Cart;
import com.foo.shoppingcart.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Oguz Ozkeroglu
 * Created on 2020.02.02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryCostCalculator {
    private double costPerDelivery;
    private double costPerProduct;
    private double fixedCost;

    public double calculateFor(Cart cart) {
        Set<Category> categories = new HashSet<>();
        Set<String> productTitles = new HashSet<>();

        cart.getCartItems().forEach(cartItem -> {
            categories.add(cartItem.getProduct().getCategory());
            productTitles.add(cartItem.getProduct().getTitle());
        });

        int numberOfDeliveries = categories.size();
        int numberOfProducts = productTitles.size();

        double deliveryCost = this.costPerDelivery * numberOfDeliveries
                + this.costPerProduct * numberOfProducts
                + this.fixedCost;

        return deliveryCost;
    }
}
