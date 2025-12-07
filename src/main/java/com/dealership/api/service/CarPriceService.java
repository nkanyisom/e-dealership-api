package com.dealership.api.service;

import com.dealership.api.entity.CarPrice;
import com.dealership.api.repository.CarPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing car price operations
 */
@Service
@Transactional
public class CarPriceService {
    
    private final CarPriceRepository carPriceRepository;
    
    public CarPriceService(CarPriceRepository carPriceRepository) {
        this.carPriceRepository = carPriceRepository;
    }
    
    /**
     * Get all car prices
     */
    @Transactional(readOnly = true)
    public List<CarPrice> getAllCarPrices() {
        return carPriceRepository.findAll();
    }
    
    /**
     * Get car price by ID
     */
    @Transactional(readOnly = true)
    public Optional<CarPrice> getCarPriceById(Long id) {
        return carPriceRepository.findById(id);
    }
    
    /**
     * Get car prices by car model
     */
    @Transactional(readOnly = true)
    public List<CarPrice> getCarPricesByCarModel(Long carModelId) {
        return carPriceRepository.findByCarModelId(carModelId);
    }
    
    /**
     * Get car prices by price type
     */
    @Transactional(readOnly = true)
    public List<CarPrice> getCarPricesByType(String priceType) {
        return carPriceRepository.findByPriceType(priceType);
    }
    
    /**
     * Get car prices within price range
     */
    @Transactional(readOnly = true)
    public List<CarPrice> getCarPricesInRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return carPriceRepository.findByPriceBetween(minPrice, maxPrice);
    }
    
    /**
     * Get currently active car prices
     */
    @Transactional(readOnly = true)
    public List<CarPrice> getActivePrices() {
        return carPriceRepository.findActivePrices(LocalDate.now());
    }
    
    /**
     * Get active prices for a specific car model
     */
    @Transactional(readOnly = true)
    public List<CarPrice> getActivePricesForCarModel(Long carModelId) {
        return carPriceRepository.findActivePricesByCarModel(carModelId, LocalDate.now());
    }
    
    /**
     * Get latest price for a car model by type
     */
    @Transactional(readOnly = true)
    public List<CarPrice> getLatestPriceByCarModelAndType(Long carModelId, String priceType) {
        return carPriceRepository.findLatestPriceByCarModelAndType(carModelId, priceType);
    }
    
    /**
     * Get current active price for a car model by type
     */
    @Transactional(readOnly = true)
    public Optional<CarPrice> getCurrentPriceByCarModelAndType(Long carModelId, String priceType) {
        return carPriceRepository.findCurrentPriceByCarModelAndType(carModelId, priceType, LocalDate.now());
    }
    
    /**
     * Get prices by date range
     */
    @Transactional(readOnly = true)
    public List<CarPrice> getPricesByDateRange(LocalDate startDate, LocalDate endDate) {
        return carPriceRepository.findByEffectiveDateBetween(startDate, endDate);
    }
    
    /**
     * Get average price by car make
     */
    @Transactional(readOnly = true)
    public Optional<BigDecimal> getAveragePriceByMake(String make) {
        return carPriceRepository.getAveragePriceByMake(make, LocalDate.now());
    }
    
    /**
     * Create a new car price
     */
    public CarPrice createCarPrice(CarPrice carPrice) {
        return carPriceRepository.save(carPrice);
    }
    
    /**
     * Update an existing car price
     */
    public CarPrice updateCarPrice(Long id, CarPrice carPriceDetails) {
        return carPriceRepository.findById(id)
                .map(carPrice -> {
                    carPrice.setPrice(carPriceDetails.getPrice());
                    carPrice.setPriceType(carPriceDetails.getPriceType());
                    carPrice.setEffectiveDate(carPriceDetails.getEffectiveDate());
                    carPrice.setExpiryDate(carPriceDetails.getExpiryDate());
                    carPrice.setNotes(carPriceDetails.getNotes());
                    return carPriceRepository.save(carPrice);
                })
                .orElseThrow(() -> new IllegalArgumentException("Car price not found with id: " + id));
    }
    
    /**
     * Delete a car price
     */
    public void deleteCarPrice(Long id) {
        if (!carPriceRepository.existsById(id)) {
            throw new IllegalArgumentException("Car price not found with id: " + id);
        }
        carPriceRepository.deleteById(id);
    }
    
    /**
     * Count prices by type
     */
    @Transactional(readOnly = true)
    public long countPricesByType(String priceType) {
        return carPriceRepository.countByPriceType(priceType);
    }
    
    /**
     * Check if car price exists
     */
    @Transactional(readOnly = true)
    public boolean carPriceExists(Long id) {
        return carPriceRepository.existsById(id);
    }
}