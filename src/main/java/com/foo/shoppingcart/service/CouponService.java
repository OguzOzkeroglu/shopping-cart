package com.foo.shoppingcart.service;

import com.foo.shoppingcart.exception.ResourceNotFoundException;
import com.foo.shoppingcart.model.Coupon;
import com.foo.shoppingcart.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Oguz Ozkeroglu
 * Created on 2020.02.01
 */

@RequiredArgsConstructor
@Service
public class CouponService {
    private final CouponRepository couponRepository;

    public Coupon findById(Long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon not found with given id: " + id));
    }

    public List<Coupon> findAll() {
        return couponRepository.findAll();
    }

    public Coupon save(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    public Coupon update(Long id, Coupon coupon) {
        couponRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        coupon.setId(id);
        return couponRepository.save(coupon);
    }

    public void delete(Long id) {
        Coupon coupon = couponRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        couponRepository.delete(coupon);
    }
}
