package com.dealership.api.service;

import com.dealership.api.entity.CarModel;
import com.dealership.api.entity.CarCategory;
import com.dealership.api.entity.CarPrice;
import com.dealership.api.repository.CarModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing car model operations
 */
@Service
@Transactional
public class CarModelService {
    
    private final CarModelRepository carModelRepository;
    
    @Autowired
    public CarModelService(CarModelRepository carModelRepository) {
        this.carModelRepository = carModelRepository;
    }
    
    /**
     * Get all car models
     */
    @Transactional(readOnly = true)
    public List<CarModel> getAllCarModels() {
        return carModelRepository.findAll();
    }
    
    /**
     * Get car model by ID
     */
    @Transactional(readOnly = true)
    public Optional<CarModel> getCarModelById(Long id) {
        return carModelRepository.findById(id);
    }
    
    /**
     * Get car models by make
     */
    @Transactional(readOnly = true)
    public List<CarModel> getCarModelsByMake(String make) {
        return carModelRepository.findByMakeIgnoreCase(make);
    }
    
    /**
     * Get car models by category
     */
    @Transactional(readOnly = true)
    public List<CarModel> getCarModelsByCategory(CarCategory category) {
        return carModelRepository.findByCategory(category);
    }
    
    /**
     * Get car models by year
     */
    @Transactional(readOnly = true)
    public List<CarModel> getCarModelsByYear(Integer year) {
        return carModelRepository.findByYear(year);
    }
    
    /**
     * Get car models by year range
     */
    @Transactional(readOnly = true)
    public List<CarModel> getCarModelsByYearRange(Integer startYear, Integer endYear) {
        return carModelRepository.findByYearBetween(startYear, endYear);
    }
    
    /**
     * Get car models by make and model name
     */
    @Transactional(readOnly = true)
    public List<CarModel> getCarModelsByMakeAndModel(String make, String model) {
        return carModelRepository.findByMakeIgnoreCaseAndModelIgnoreCase(make, model);
    }
    
    /**
     * Get car models by dealership
     */
    @Transactional(readOnly = true)
    public List<CarModel> getCarModelsByDealership(Long dealershipId) {
        return carModelRepository.findByDealershipId(dealershipId);
    }
    
    /**
     * Get car models by make and category
     */
    @Transactional(readOnly = true)
    public List<CarModel> getCarModelsByMakeAndCategory(String make, CarCategory category) {
        return carModelRepository.findByMakeIgnoreCaseAndCategory(make, category);
    }
    
    /**
     * Get latest car models by make
     */
    @Transactional(readOnly = true)
    public List<CarModel> getLatestCarModelsByMake() {
        return carModelRepository.findLatestModelsByMake();
    }
    
    /**
     * Search car models by description
     */
    @Transactional(readOnly = true)
    public List<CarModel> searchCarModelsByDescription(String keyword) {
        return carModelRepository.findByDescriptionContainingIgnoreCase(keyword);
    }
    
    /**
     * Get car models by dealership location
     */
    @Transactional(readOnly = true)
    public List<CarModel> getCarModelsByDealershipLocation(String location) {
        return carModelRepository.findByDealershipLocation(location);
    }
    
    /**
     * Create a new car model
     */
    public CarModel createCarModel(CarModel carModel) {
        return carModelRepository.save(carModel);
    }
    
    /**
     * Update an existing car model
     */
    public CarModel updateCarModel(Long id, CarModel carModelDetails) {
        return carModelRepository.findById(id)
                .map(carModel -> {
                    carModel.setMake(carModelDetails.getMake());
                    carModel.setModel(carModelDetails.getModel());
                    carModel.setYear(carModelDetails.getYear());
                    carModel.setCategory(carModelDetails.getCategory());
                    carModel.setDescription(carModelDetails.getDescription());
                    return carModelRepository.save(carModel);
                })
                .orElseThrow(() -> new IllegalArgumentException("Car model not found with id: " + id));
    }
    
    /**
     * Delete a car model
     */
    public void deleteCarModel(Long id) {
        if (!carModelRepository.existsById(id)) {
            throw new IllegalArgumentException("Car model not found with id: " + id);
        }
        carModelRepository.deleteById(id);
    }
    
    /**
     * Add a price to a car model
     */
    public CarModel addPriceToCarModel(Long carModelId, CarPrice carPrice) {
        return carModelRepository.findById(carModelId)
                .map(carModel -> {
                    carModel.addCarPrice(carPrice);
                    return carModelRepository.save(carModel);
                })
                .orElseThrow(() -> new IllegalArgumentException("Car model not found with id: " + carModelId));
    }
    
    /**
     * Remove a price from a car model
     */
    public CarModel removePriceFromCarModel(Long carModelId, CarPrice carPrice) {
        return carModelRepository.findById(carModelId)
                .map(carModel -> {
                    carModel.removeCarPrice(carPrice);
                    return carModelRepository.save(carModel);
                })
                .orElseThrow(() -> new IllegalArgumentException("Car model not found with id: " + carModelId));
    }
    
    /**
     * Count car models by category
     */
    @Transactional(readOnly = true)
    public long countCarModelsByCategory(CarCategory category) {
        return carModelRepository.countByCategory(category);
    }
    
    /**
     * Check if car model exists
     */
    @Transactional(readOnly = true)
    public boolean carModelExists(Long id) {
        return carModelRepository.existsById(id);
    }
}