package com.foo.shoppingcart.service;

import com.foo.shoppingcart.exception.ResourceNotFoundException;
import com.foo.shoppingcart.model.Coupon;
import com.foo.shoppingcart.repository.CouponRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Oguz Ozkeroglu
 * Created on 2020.02.01
 */

@ExtendWith(MockitoExtension.class)
class CouponServiceTest {
    @InjectMocks
    private CouponService couponService;

    @Mock
    private CouponRepository repository;

    @Test
    void shouldRead() {
        //given
        Coupon coupon = Coupon.builder().id(1L).rate(5.0).build();

        when(repository.findById(1L)).thenReturn(Optional.ofNullable(coupon));

        //when
        Coupon actual = couponService.findById(1L);

        //then
        verify(repository, times(1)).findById(any(Long.class));
        assertThat(actual.getId(), is(notNullValue()));
        assertThat(actual, equalTo(coupon));
    }

    @Test
    void shouldThrowExceptionWhenNotFound() {
        //given
        when(repository.findById(1L)).thenReturn(Optional.empty());

        //when
        RuntimeException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> couponService.findById(1L)
        );

        //then
        assertTrue(thrown.getMessage().contains("Coupon not found"));
    }

    @Test
    void shouldFindAll() {
        //given
        Coupon coupon1 = Coupon.builder().id(1L).build();
        Coupon coupon2 = Coupon.builder().id(2L).build();

        when(repository.findAll()).thenReturn(Arrays.asList(coupon1, coupon2));

        //when
        List<Coupon> actual = couponService.findAll();

        //then
        assertThat(actual, hasItems(coupon1, coupon2));
    }

    @Test
    void shouldCreateCoupon() {
        //given
        Coupon coupon = Coupon.builder().id(1L).rate(5.0).build();

        when(repository.save(coupon)).thenReturn(coupon);

        //when
        Coupon actual = couponService.save(coupon);

        //then
        verify(repository, times(1)).save(any(Coupon.class));
        assertThat(actual.getId(), is(notNullValue()));
        assertThat(actual, equalTo(coupon));
    }

    @Test
    void shouldUpdateCouponWithNewValues() {
        //given
        Coupon coupon = Coupon.builder().id(1L).rate(5.0).build();

        Coupon couponUpdated = Coupon.builder().id(1L).rate(15.0).minAmount(15.0).build();

        when(repository.findById(1L)).thenReturn(Optional.ofNullable(coupon));
        when(repository.save(any(Coupon.class))).thenReturn(couponUpdated);

        //when
        Coupon actual = couponService.update(1L, couponUpdated);

        //then
        verify(repository, times(1)).save(any(Coupon.class));
        assertThat(actual.getId(), is(notNullValue()));
        assertThat(actual.getRate(), equalTo(15.0));
        assertThat(actual.getMinAmount(), equalTo(15.0));
        assertThat(actual, equalTo(couponUpdated));
    }

    @Test
    void shouldThrowExceptionWhenNotFoundForUpdate() {
        //given
        Coupon coupon = Coupon.builder().id(1L).rate(5.0).build();

        when(repository.findById(1L)).thenReturn(Optional.empty());

        //when
        RuntimeException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> couponService.update(1L, coupon)
        );

        //then
        assertTrue(thrown.getMessage().contains("Coupon not found"));
    }

    @Test
    void shouldDeleteCoupon() {
        //given
        Coupon coupon = Coupon.builder().id(1L).rate(5.0).build();

        when(repository.findById(1L)).thenReturn(Optional.of(coupon));

        //when
        couponService.delete(1L);

        //then
        verify(repository, times(1)).delete(coupon);
    }

    @Test
    void shouldThrowExceptionWhenNotFoundForDelete() {
        //given
        when(repository.findById(1L)).thenReturn(Optional.empty());

        //when
        RuntimeException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> couponService.delete(1L)
        );

        //then
        assertTrue(thrown.getMessage().contains("Coupon not found"));
    }
}