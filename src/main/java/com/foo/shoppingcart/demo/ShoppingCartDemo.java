package com.foo.shoppingcart.demo;

import com.foo.shoppingcart.model.*;
import com.foo.shoppingcart.model.enums.DiscountType;
import com.foo.shoppingcart.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @author Oguz Ozkeroglu
 * Created on 2020.02.02
 */

@RequiredArgsConstructor
@Component
@Slf4j
public class ShoppingCartDemo implements CommandLineRunner {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final CartItemService cartItemService;
    private final CartService cartService;
    private final CampaignService campaignService;
    private final CouponService couponService;

    private Category food, electronic;
    private Product apple, almond;
    private CartItem cartItemApple, cartItemAlmond;
    private Cart cart;
    private Campaign campaign1, campaign2, campaign3;
    private Coupon coupon1, coupon2;

    @Override
    public void run(String... args) {
        log.info("Shopping Cart Demo");

        saveCategories();
        saveProducts();
        saveCartItems();
        saveCart();
        saveCampaigns();
        saveCoupons();

        applyDiscountsToCart();
        applyCouponToCart();

        cart.print();
    }

    private void applyDiscountsToCart() {
        cart.applyDiscounts(campaign1, campaign2, campaign3);
    }

    private void applyCouponToCart() {
        cart.applyCoupon(coupon1);
    }

    private void saveCategories() {
        food = Category.builder().title("food").build();
        electronic = Category.builder().title("electronic").build();

        categoryService.save(food);
        categoryService.save(electronic);

        log.info("Category saved " + food);
        log.info("Category saved " + electronic);
    }

    private void saveProducts() {
        apple = Product.builder().title("Apple").price(100.0).category(food).build();
        almond = Product.builder().title("Almonds").price(150.0).category(food).build();

        productService.save(apple);
        productService.save(almond);

        log.info("Product saved " + apple);
        log.info("Product saved " + almond);
    }

    private void saveCartItems() {
        cartItemApple = CartItem.builder()
                .product(apple).quantity(3)
                .price(apple.getPrice())
                .build();
        cartItemAlmond = CartItem.builder()
                .product(almond).quantity(1)
                .price(almond.getPrice())
                .build();

        cartItemService.save(cartItemApple);
        cartItemService.save(cartItemAlmond);

        log.info("Cart item saved " + cartItemApple);
        log.info("Cart item saved " + cartItemAlmond);
    }

    private void saveCart() {
        cart = Cart.builder().cartItems(new ArrayList<>()).build();

        cart.addItem(cartItemApple);
        cart.addItem(cartItemAlmond);

        cartService.save(cart);

        log.info("Cart saved " + cart);
    }

    private void saveCampaigns() {
        campaign1 = Campaign.builder()
                .campaignCategory(food)
                .rate(20.0)
                .minQuantity(3)
                .discountType(DiscountType.Rate)
                .build();

        campaign2 = Campaign.builder()
                .campaignCategory(food)
                .rate(50.0)
                .minQuantity(5)
                .discountType(DiscountType.Rate)
                .build();

        campaign3 = Campaign.builder()
                .campaignCategory(food)
                .rate(5.0)
                .minQuantity(5)
                .discountType(DiscountType.Amount)
                .build();

        campaignService.save(campaign1);
        campaignService.save(campaign2);
        campaignService.save(campaign3);

        log.info("Campaign saved " + campaign1);
        log.info("Campaign saved " + campaign2);
        log.info("Campaign saved " + campaign3);
    }

    private void saveCoupons() {
        coupon1 = Coupon.builder().minAmount(100.0).rate(10.0).discountType(DiscountType.Rate).build();
        coupon2 = Coupon.builder().minAmount(500.0).rate(20.0).discountType(DiscountType.Rate).build();

        couponService.save(coupon1);
        couponService.save(coupon2);

        log.info("Coupon saved " + coupon1);
        log.info("Coupon saved " + coupon2);
    }
}
