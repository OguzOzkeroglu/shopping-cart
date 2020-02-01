package com.foo.shoppingcart.service;

import com.foo.shoppingcart.exception.ResourceNotFoundException;
import com.foo.shoppingcart.model.Campaign;
import com.foo.shoppingcart.repository.CampaignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Oguz Ozkeroglu
 * Created on 2020.02.01
 */

@RequiredArgsConstructor
@Service
public class CampaignService {
    private final CampaignRepository campaignRepository;

    public Campaign findById(Long id) {
        return campaignRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found with given id: " + id));
    }

    public List<Campaign> findAll() {
        return campaignRepository.findAll();
    }

    public Campaign save(Campaign campaign) {
        return campaignRepository.save(campaign);
    }

    public Campaign update(Long id, Campaign campaign) {
        campaignRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        campaign.setId(id);
        return campaignRepository.save(campaign);
    }

    public void delete(Long id) {
        Campaign campaign = campaignRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        campaignRepository.delete(campaign);
    }
}
