package com.foo.shoppingcart.repository;

import com.foo.shoppingcart.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Oguz Ozkeroglu
 * Created on 2020.02.01
 */
public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
