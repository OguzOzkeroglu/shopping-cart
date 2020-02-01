package com.foo.shoppingcart.service;

import com.foo.shoppingcart.exception.ResourceNotFoundException;
import com.foo.shoppingcart.model.Product;
import com.foo.shoppingcart.repository.ProductRepository;
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
class ProductServiceTest {
    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository repository;

    @Test
    void shouldRead() {
        //given
        Product product = Product.builder().id(1L).build();

        when(repository.findById(1L)).thenReturn(Optional.ofNullable(product));

        //when
        Product actual = productService.findById(1L);

        //then
        verify(repository, times(1)).findById(any(Long.class));
        assertThat(actual.getId(), is(notNullValue()));
        assertThat(actual, equalTo(product));
    }

    @Test
    void shouldThrowExceptionWhenNotFound() {
        //given
        when(repository.findById(1L)).thenReturn(Optional.empty());

        //when
        RuntimeException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> productService.findById(1L)
        );

        //then
        assertTrue(thrown.getMessage().contains("Product not found"));
    }

    @Test
    void shouldFindAll() {
        //given
        Product product1 = Product.builder().id(1L).build();
        Product product2 = Product.builder().id(2L).build();

        when(repository.findAll()).thenReturn(Arrays.asList(product1, product2));

        //when
        List<Product> actual = productService.findAll();

        //then
        assertThat(actual, hasItems(product1, product2));
    }

    @Test
    void shouldCreateProduct() {
        //given
        Product product = Product.builder().id(1L).build();

        when(repository.save(product)).thenReturn(product);

        //when
        Product actual = productService.save(product);

        //then
        verify(repository, times(1)).save(any(Product.class));
        assertThat(actual.getId(), is(notNullValue()));
        assertThat(actual, equalTo(product));
    }

    @Test
    void shouldUpdateProductWithNewValues() {
        //given
        Product product = Product.builder().id(1L).build();

        Product productUpdated = Product.builder().id(1L).title("title").build();

        when(repository.findById(1L)).thenReturn(Optional.ofNullable(product));
        when(repository.save(any(Product.class))).thenReturn(productUpdated);

        //when
        Product actual = productService.update(1L, productUpdated);

        //then
        verify(repository, times(1)).save(any(Product.class));
        assertThat(actual.getId(), is(notNullValue()));
        assertThat(actual.getTitle(), equalTo("title"));
        assertThat(actual, equalTo(productUpdated));
    }

    @Test
    void shouldThrowExceptionWhenNotFoundForUpdate() {
        //given
        Product product = Product.builder().id(1L).build();

        when(repository.findById(1L)).thenReturn(Optional.empty());

        //when
        RuntimeException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> productService.update(1L, product)
        );

        //then
        assertTrue(thrown.getMessage().contains("Product not found"));
    }

    @Test
    void shouldDeleteProduct() {
        //given
        Product product = Product.builder().id(1L).build();

        when(repository.findById(1L)).thenReturn(Optional.of(product));

        //when
        productService.delete(1L);

        //then
        verify(repository, times(1)).delete(product);
    }

    @Test
    void shouldThrowExceptionWhenNotFoundForDelete() {
        //given
        when(repository.findById(1L)).thenReturn(Optional.empty());

        //when
        RuntimeException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> productService.delete(1L)
        );

        //then
        assertTrue(thrown.getMessage().contains("Product not found"));
    }
}