package com.foo.shoppingcart.service;

import com.foo.shoppingcart.exception.ResourceNotFoundException;
import com.foo.shoppingcart.model.Campaign;
import com.foo.shoppingcart.repository.CampaignRepository;
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
class CampaignServiceTest {
    @InjectMocks
    private CampaignService campaignService;

    @Mock
    private CampaignRepository repository;

    @Test
    void shouldRead() {
        //given
        Campaign campaign = Campaign.builder()
                .id(1L)
                .minQuantity(5)
                .rate(5.0)
                .build();

        when(repository.findById(1L)).thenReturn(Optional.ofNullable(campaign));

        //when
        Campaign actual = campaignService.findById(1L);

        //then
        verify(repository, times(1)).findById(any(Long.class));
        assertThat(actual.getId(), is(notNullValue()));
        assertThat(actual.getMinQuantity(), equalTo(5));
        assertThat(actual, equalTo(campaign));
    }

    @Test
    void shouldThrowExceptionWhenNotFound() {
        //given
        when(repository.findById(1L)).thenReturn(Optional.empty());

        //when
        RuntimeException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> campaignService.findById(1L)
        );

        //then
        assertTrue(thrown.getMessage().contains("Campaign not found"));
    }

    @Test
    void shouldFindAll() {
        //given
        Campaign campaign1 = Campaign.builder().id(1L).build();
        Campaign campaign2 = Campaign.builder().id(2L).build();

        when(repository.findAll()).thenReturn(Arrays.asList(campaign1, campaign2));

        //when
        List<Campaign> actual = campaignService.findAll();

        //then
        assertThat(actual, hasItems(campaign1, campaign2));
    }

    @Test
    void shouldCreateCampaign() {
        //given
        Campaign campaign = Campaign.builder()
                .id(1L)
                .minQuantity(5)
                .rate(5.0)
                .build();

        when(repository.save(campaign)).thenReturn(campaign);

        //when
        Campaign actual = campaignService.save(campaign);

        //then
        verify(repository, times(1)).save(any(Campaign.class));
        assertThat(actual.getId(), is(notNullValue()));
        assertThat(actual.getMinQuantity(), equalTo(5));
        assertThat(actual, equalTo(campaign));
    }

    @Test
    void shouldUpdateCampaignWithNewValues() {
        //given
        Campaign campaign = Campaign.builder()
                .id(1L)
                .minQuantity(5)
                .rate(5.0)
                .build();

        Campaign campaignUpdated = Campaign.builder()
                .id(1L)
                .minQuantity(15)
                .rate(15.0)
                .build();

        when(repository.findById(1L)).thenReturn(Optional.ofNullable(campaign));
        when(repository.save(any(Campaign.class))).thenReturn(campaignUpdated);

        //when
        Campaign actual = campaignService.update(1L, campaignUpdated);

        //then
        verify(repository, times(1)).save(any(Campaign.class));
        assertThat(actual.getId(), is(notNullValue()));
        assertThat(actual.getMinQuantity(), equalTo(15));
        assertThat(actual.getRate(), equalTo(15.0));
        assertThat(actual, equalTo(campaignUpdated));
    }

    @Test
    void shouldThrowExceptionWhenNotFoundForUpdate() {
        //given
        Campaign campaign = Campaign.builder()
                .id(1L)
                .minQuantity(5)
                .rate(5.0)
                .build();

        when(repository.findById(1L)).thenReturn(Optional.empty());

        //when
        RuntimeException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> campaignService.update(1L, campaign)
        );

        //then
        assertTrue(thrown.getMessage().contains("Campaign not found"));
    }

    @Test
    void shouldDeleteCampaign() {
        //given
        Campaign campaign = Campaign.builder()
                .id(1L)
                .minQuantity(5)
                .rate(5.0)
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(campaign));

        //when
        campaignService.delete(1L);

        //then
        verify(repository, times(1)).delete(campaign);
    }

    @Test
    void shouldThrowExceptionWhenNotFoundForDelete() {
        //given
        when(repository.findById(1L)).thenReturn(Optional.empty());

        //when
        RuntimeException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> campaignService.delete(1L)
        );

        //then
        assertTrue(thrown.getMessage().contains("Campaign not found"));
    }
}