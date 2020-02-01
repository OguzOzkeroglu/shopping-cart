package com.foo.shoppingcart.service;

import com.foo.shoppingcart.exception.ResourceNotFoundException;
import com.foo.shoppingcart.model.Cart;
import com.foo.shoppingcart.model.Coupon;
import com.foo.shoppingcart.repository.CartRepository;
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
class CartServiceTest {
    @InjectMocks
    private CartService cartService;

    @Mock
    private CartRepository repository;

    @Test
    void shouldRead() {
        //given
        Cart cart = Cart.builder().id(1L).build();

        when(repository.findById(1L)).thenReturn(Optional.ofNullable(cart));

        //when
        Cart actual = cartService.findById(1L);

        //then
        verify(repository, times(1)).findById(any(Long.class));
        assertThat(actual.getId(), is(notNullValue()));
        assertThat(actual, equalTo(cart));
    }

    @Test
    void shouldThrowExceptionWhenNotFound() {
        //given
        when(repository.findById(1L)).thenReturn(Optional.empty());

        //when
        RuntimeException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> cartService.findById(1L)
        );

        //then
        assertTrue(thrown.getMessage().contains("Cart not found"));
    }

    @Test
    void shouldFindAll() {
        //given
        Cart cart1 = Cart.builder().id(1L).build();
        Cart cart2 = Cart.builder().id(2L).build();

        when(repository.findAll()).thenReturn(Arrays.asList(cart1, cart2));

        //when
        List<Cart> actual = cartService.findAll();

        //then
        assertThat(actual, hasItems(cart1, cart2));
    }

    @Test
    void shouldCreateCart() {
        //given
        Cart cart = Cart.builder().id(1L).build();

        when(repository.save(cart)).thenReturn(cart);

        //when
        Cart actual = cartService.save(cart);

        //then
        verify(repository, times(1)).save(any(Cart.class));
        assertThat(actual.getId(), is(notNullValue()));
        assertThat(actual, equalTo(cart));
    }

    @Test
    void shouldUpdateCartWithNewValues() {
        //given
        Cart cart = Cart.builder().id(1L).build();

        Coupon coupon = Coupon.builder().id(1L).rate(15.0).build();

        Cart cartUpdated = Cart.builder().id(1L).coupon(coupon).build();

        when(repository.findById(1L)).thenReturn(Optional.ofNullable(cart));
        when(repository.save(any(Cart.class))).thenReturn(cartUpdated);

        //when
        Cart actual = cartService.update(1L, cartUpdated);

        //then
        verify(repository, times(1)).save(any(Cart.class));
        assertThat(actual.getId(), is(notNullValue()));
        assertThat(actual.getCoupon().getRate(), equalTo(15.0));
        assertThat(actual, equalTo(cartUpdated));
    }

    @Test
    void shouldThrowExceptionWhenNotFoundForUpdate() {
        //given
        Cart cart = Cart.builder().id(1L).build();

        when(repository.findById(1L)).thenReturn(Optional.empty());

        //when
        RuntimeException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> cartService.update(1L, cart)
        );

        //then
        assertTrue(thrown.getMessage().contains("Cart not found"));
    }

    @Test
    void shouldDeleteCart() {
        //given
        Cart cart = Cart.builder().id(1L).build();

        when(repository.findById(1L)).thenReturn(Optional.of(cart));

        //when
        cartService.delete(1L);

        //then
        verify(repository, times(1)).delete(cart);
    }

    @Test
    void shouldThrowExceptionWhenNotFoundForDelete() {
        //given
        when(repository.findById(1L)).thenReturn(Optional.empty());

        //when
        RuntimeException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> cartService.delete(1L)
        );

        //then
        assertTrue(thrown.getMessage().contains("Cart not found"));
    }
}