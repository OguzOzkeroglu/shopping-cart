package com.foo.shoppingcart.model;

import com.foo.shoppingcart.model.enums.DiscountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Oguz Ozkeroglu
 * Created on 2020.01.26
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart couponCart;

    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    private Double rate;
    private Double minAmount;
}
