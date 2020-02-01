package com.foo.shoppingcart.service;

import com.foo.shoppingcart.exception.ResourceNotFoundException;
import com.foo.shoppingcart.model.Category;
import com.foo.shoppingcart.repository.CategoryRepository;
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
class CategoryServiceTest {
    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository repository;

    @Test
    void shouldRead() {
        //given
        Category category = Category.builder().id(1L).build();

        when(repository.findById(1L)).thenReturn(Optional.ofNullable(category));

        //when
        Category actual = categoryService.findById(1L);

        //then
        verify(repository, times(1)).findById(any(Long.class));
        assertThat(actual.getId(), is(notNullValue()));
        assertThat(actual, equalTo(category));
    }

    @Test
    void shouldThrowExceptionWhenNotFound() {
        //given
        when(repository.findById(1L)).thenReturn(Optional.empty());

        //when
        RuntimeException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> categoryService.findById(1L)
        );

        //then
        assertTrue(thrown.getMessage().contains("Category not found"));
    }

    @Test
    void shouldFindAll() {
        //given
        Category category1 = Category.builder().id(1L).build();
        Category category2 = Category.builder().id(2L).build();

        when(repository.findAll()).thenReturn(Arrays.asList(category1, category2));

        //when
        List<Category> actual = categoryService.findAll();

        //then
        assertThat(actual, hasItems(category1, category2));
    }

    @Test
    void shouldCreateCategory() {
        //given
        Category category = Category.builder().id(1L).build();

        when(repository.save(category)).thenReturn(category);

        //when
        Category actual = categoryService.save(category);

        //then
        verify(repository, times(1)).save(any(Category.class));
        assertThat(actual.getId(), is(notNullValue()));
        assertThat(actual, equalTo(category));
    }

    @Test
    void shouldUpdateCategoryWithNewValues() {
        //given
        Category category = Category.builder().id(1L).build();

        Category categoryUpdated = Category.builder().id(1L).title("title").build();

        when(repository.findById(1L)).thenReturn(Optional.ofNullable(category));
        when(repository.save(any(Category.class))).thenReturn(categoryUpdated);

        //when
        Category actual = categoryService.update(1L, categoryUpdated);

        //then
        verify(repository, times(1)).save(any(Category.class));
        assertThat(actual.getId(), is(notNullValue()));
        assertThat(actual.getTitle(), equalTo("title"));
        assertThat(actual, equalTo(categoryUpdated));
    }

    @Test
    void shouldThrowExceptionWhenNotFoundForUpdate() {
        //given
        Category category = Category.builder().id(1L).build();

        when(repository.findById(1L)).thenReturn(Optional.empty());

        //when
        RuntimeException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> categoryService.update(1L, category)
        );

        //then
        assertTrue(thrown.getMessage().contains("Category not found"));
    }

    @Test
    void shouldDeleteCategory() {
        //given
        Category category = Category.builder().id(1L).build();

        when(repository.findById(1L)).thenReturn(Optional.of(category));

        //when
        categoryService.delete(1L);

        //then
        verify(repository, times(1)).delete(category);
    }

    @Test
    void shouldThrowExceptionWhenNotFoundForDelete() {
        //given
        when(repository.findById(1L)).thenReturn(Optional.empty());

        //when
        RuntimeException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> categoryService.delete(1L)
        );

        //then
        assertTrue(thrown.getMessage().contains("Category not found"));
    }

}