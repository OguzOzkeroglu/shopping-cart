package com.foo.shoppingcart.service;

import com.foo.shoppingcart.exception.ResourceNotFoundException;
import com.foo.shoppingcart.model.CartItem;
import com.foo.shoppingcart.repository.CartItemRepository;
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
class CartItemServiceTest {
    @InjectMocks
    private CartItemService cartItemService;

    @Mock
    private CartItemRepository repository;

    @Test
    void shouldRead() {
        //given
        CartItem cartItem = CartItem.builder().id(1L).quantity(5).build();

        when(repository.findById(1L)).thenReturn(Optional.ofNullable(cartItem));

        //when
        CartItem actual = cartItemService.findById(1L);

        //then
        verify(repository, times(1)).findById(any(Long.class));
        assertThat(actual.getId(), is(notNullValue()));
        assertThat(actual.getQuantity(), equalTo(5));
        assertThat(actual, equalTo(cartItem));
    }

    @Test
    void shouldThrowExceptionWhenNotFound() {
        //given
        when(repository.findById(1L)).thenReturn(Optional.empty());

        //when
        RuntimeException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> cartItemService.findById(1L)
        );

        //then
        assertTrue(thrown.getMessage().contains("CartItem not found"));
    }

    @Test
    void shouldFindAll() {
        //given
        CartItem cartItem1 = CartItem.builder().id(1L).build();
        CartItem cartItem2 = CartItem.builder().id(2L).build();

        when(repository.findAll()).thenReturn(Arrays.asList(cartItem1, cartItem2));

        //when
        List<CartItem> actual = cartItemService.findAll();

        //then
        assertThat(actual, hasItems(cartItem1, cartItem2));
    }

    @Test
    void shouldCreateCartItem() {
        //given
        CartItem cartItem = CartItem.builder().id(1L).quantity(5).build();

        when(repository.save(cartItem)).thenReturn(cartItem);

        //when
        CartItem actual = cartItemService.save(cartItem);

        //then
        verify(repository, times(1)).save(any(CartItem.class));
        assertThat(actual.getId(), is(notNullValue()));
        assertThat(actual.getQuantity(), equalTo(5));
        assertThat(actual, equalTo(cartItem));
    }

    @Test
    void shouldUpdateCartItemWithNewValues() {
        //given
        CartItem cartItem = CartItem.builder().id(1L).quantity(5).build();

        CartItem cartItemUpdated = CartItem.builder().id(1L).quantity(15).build();

        when(repository.findById(1L)).thenReturn(Optional.ofNullable(cartItem));
        when(repository.save(any(CartItem.class))).thenReturn(cartItemUpdated);

        //when
        CartItem actual = cartItemService.update(1L, cartItemUpdated);

        //then
        verify(repository, times(1)).save(any(CartItem.class));
        assertThat(actual.getId(), is(notNullValue()));
        assertThat(actual.getQuantity(), equalTo(15));
        assertThat(actual, equalTo(cartItemUpdated));
    }

    @Test
    void shouldThrowExceptionWhenNotFoundForUpdate() {
        //given
        CartItem cartItem = CartItem.builder().id(1L).quantity(5).build();

        when(repository.findById(1L)).thenReturn(Optional.empty());

        //when
        RuntimeException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> cartItemService.update(1L, cartItem)
        );

        //then
        assertTrue(thrown.getMessage().contains("CartItem not found"));
    }

    @Test
    void shouldDeleteCartItem() {
        //given
        CartItem cartItem = CartItem.builder().id(1L).quantity(5).build();

        when(repository.findById(1L)).thenReturn(Optional.of(cartItem));

        //when
        cartItemService.delete(1L);

        //then
        verify(repository, times(1)).delete(cartItem);
    }

    @Test
    void shouldThrowExceptionWhenNotFoundForDelete() {
        //given
        when(repository.findById(1L)).thenReturn(Optional.empty());

        //when
        RuntimeException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> cartItemService.delete(1L)
        );

        //then
        assertTrue(thrown.getMessage().contains("CartItem not found"));
    }
}