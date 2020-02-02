package com.foo.shoppingcart.model;

import com.foo.shoppingcart.util.DeliveryCostCalculator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Oguz Ozkeroglu
 * Created on 2020.01.26
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItems = new ArrayList<>();

    @OneToOne(mappedBy = "couponCart")
    private Coupon coupon;

    // Utility methods
    
    public void addItem(CartItem cartItem) {
        this.cartItems.add(cartItem);
    }

    public void applyDiscounts(Campaign... campaigns) {
        for (Campaign campaign: campaigns) {
            if (isApplicable(campaign)) {
                //TODO: implement this
            }
        }
    }

    public void applyCoupon(Coupon coupon) {
        if (!isApplicable(coupon)) {
            return;
        }

        this.coupon = coupon;
    }

    public double getTotalAmountAfterDiscounts() {
        //TODO: implement method
        return 0.0;
    }

    public double getCouponDiscount() {
        //TODO: implement method
        return 0.0;
    }

    public double getCampaignDiscount() {
        //TODO: implement method
        return 0.0;
    }

    public double getDeliveryCost() {
        DeliveryCostCalculator deliveryCostCalculator = new DeliveryCostCalculator(0.99, 0.99,2.99);
        return deliveryCostCalculator.calculateFor(this);
    }

    public void print() {
        StringBuilder sb = new StringBuilder();

        cartItems.forEach(cartItem -> {
            sb.append("Category name: ").append(cartItem.getProduct().getCategory().getTitle()).append(", ");
            sb.append("Product name: ").append(cartItem.getProduct().getTitle()).append(", ");
            sb.append("Quantity: ").append(cartItem.getQuantity()).append(", ");
            sb.append("Unit price: ").append(cartItem.getPrice()).append(", ");
            sb.append("Total price: ").append(cartItem.getPrice() * cartItem.getQuantity()).append(", ");
            sb.append("Total discount applied: ").append("TODO: calculate discount applied").append(", "); //TODO:
            sb.append("\n");
        });

        sb.append("Total amount: ").append(getTotalAmountAfterDiscounts()).append(", ");
        sb.append("Delivery cost: ").append(getDeliveryCost()).append("\n");

        System.out.println(sb.toString());
    }

    private double getTotalAmount() {
        return cartItems.stream()
                .mapToDouble(cartItem -> cartItem.getQuantity() * cartItem.getPrice())
                .sum();
    }

    private boolean isApplicable(Campaign campaign) {
        Category campaignCategory = campaign.getCampaignCategory();

        List<CartItem> cartItems = this.getCartItems().stream()
                .filter(cartItem -> cartItem.getProduct().getCategory().equals(campaignCategory))
                .collect(Collectors.toList());

        if (cartItems.isEmpty()) {
            return false;        // there is no item with given campaign category in shopping cart
        }

        int totalQuantityWithGivenCategory = cartItems.stream()
                .mapToInt(item -> item.getQuantity())
                .sum();

        if (campaign.getMinQuantity() > totalQuantityWithGivenCategory) {
            return false;       // cart has less product quantity than required for this campaign
        }

        return true;
    }

    private boolean isApplicable(Coupon coupon) {
        return !(coupon.getMinAmount() > getTotalAmount());
    }
}
