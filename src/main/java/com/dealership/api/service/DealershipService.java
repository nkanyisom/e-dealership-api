package com.dealership.api.service;

import com.dealership.api.entity.Dealership;
import com.dealership.api.entity.CarModel;
import com.dealership.api.repository.DealershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing dealership operations
 */
@Service
@Transactional
public class DealershipService {
    
    private final DealershipRepository dealershipRepository;
    
    @Autowired
    public DealershipService(DealershipRepository dealershipRepository) {
        this.dealershipRepository = dealershipRepository;
    }
    
    /**
     * Get all dealerships
     */
    @Transactional(readOnly = true)
    public List<Dealership> getAllDealerships() {
        return dealershipRepository.findAll();
    }
    
    /**
     * Get dealership by ID
     */
    @Transactional(readOnly = true)
    public Optional<Dealership> getDealershipById(Long id) {
        return dealershipRepository.findById(id);
    }
    
    /**
     * Get dealership by name
     */
    @Transactional(readOnly = true)
    public Optional<Dealership> getDealershipByName(String name) {
        return dealershipRepository.findByNameIgnoreCase(name);
    }
    
    /**
     * Find dealerships by location
     */
    @Transactional(readOnly = true)
    public List<Dealership> getDealershipsByLocation(String location) {
        return dealershipRepository.findByLocationContainingIgnoreCase(location);
    }
    
    /**
     * Get dealerships with car models in stock
     */
    @Transactional(readOnly = true)
    public List<Dealership> getDealershipsWithCarModels() {
        return dealershipRepository.findDealershipsWithCarModels();
    }
    
    /**
     * Find dealerships by location and car make
     */
    @Transactional(readOnly = true)
    public List<Dealership> getDealershipsByLocationAndCarMake(String location, String make) {
        return dealershipRepository.findByLocationAndCarMake(location, make);
    }
    
    /**
     * Create a new dealership
     */
    public Dealership createDealership(Dealership dealership) {
        // Check if dealership with same name already exists
        Optional<Dealership> existingDealership = dealershipRepository.findByNameIgnoreCase(dealership.getName());
        if (existingDealership.isPresent()) {
            throw new IllegalArgumentException("Dealership with name '" + dealership.getName() + "' already exists");
        }
        
        return dealershipRepository.save(dealership);
    }
    
    /**
     * Update an existing dealership
     */
    public Dealership updateDealership(Long id, Dealership dealershipDetails) {
        return dealershipRepository.findById(id)
                .map(dealership -> {
                    dealership.setName(dealershipDetails.getName());
                    dealership.setLocation(dealershipDetails.getLocation());
                    dealership.setPhoneNumber(dealershipDetails.getPhoneNumber());
                    dealership.setEmail(dealershipDetails.getEmail());
                    return dealershipRepository.save(dealership);
                })
                .orElseThrow(() -> new IllegalArgumentException("Dealership not found with id: " + id));
    }
    
    /**
     * Delete a dealership
     */
    public void deleteDealership(Long id) {
        if (!dealershipRepository.existsById(id)) {
            throw new IllegalArgumentException("Dealership not found with id: " + id);
        }
        dealershipRepository.deleteById(id);
    }
    
    /**
     * Add a car model to a dealership
     */
    public Dealership addCarModelToDealership(Long dealershipId, CarModel carModel) {
        return dealershipRepository.findById(dealershipId)
                .map(dealership -> {
                    dealership.addCarModel(carModel);
                    return dealershipRepository.save(dealership);
                })
                .orElseThrow(() -> new IllegalArgumentException("Dealership not found with id: " + dealershipId));
    }
    
    /**
     * Remove a car model from a dealership
     */
    public Dealership removeCarModelFromDealership(Long dealershipId, CarModel carModel) {
        return dealershipRepository.findById(dealershipId)
                .map(dealership -> {
                    dealership.removeCarModel(carModel);
                    return dealershipRepository.save(dealership);
                })
                .orElseThrow(() -> new IllegalArgumentException("Dealership not found with id: " + dealershipId));
    }
    
    /**
     * Count dealerships by location
     */
    @Transactional(readOnly = true)
    public long countDealershipsByLocation(String location) {
        return dealershipRepository.countByLocationContainingIgnoreCase(location);
    }
    
    /**
     * Check if dealership exists
     */
    @Transactional(readOnly = true)
    public boolean dealershipExists(Long id) {
        return dealershipRepository.existsById(id);
    }
}