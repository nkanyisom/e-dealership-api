package com.dealership.api.repository;

import com.dealership.api.entity.CarPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for CarPrice entity
 */
@Repository
public interface CarPriceRepository extends JpaRepository<CarPrice, Long> {
    
    /**
     * Find car prices by car model ID
     */
    List<CarPrice> findByCarModelId(Long carModelId);
    
    /**
     * Find car prices by price type
     */
    List<CarPrice> findByPriceType(String priceType);
    
    /**
     * Find car prices within a price range
     */
    List<CarPrice> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
    /**
     * Find current active car prices (effective date <= today and expiry date >= today or null)
     */
    @Query("SELECT cp FROM CarPrice cp WHERE cp.effectiveDate <= :currentDate AND " +
           "(cp.expiryDate IS NULL OR cp.expiryDate >= :currentDate)")
    List<CarPrice> findActivePrices(@Param("currentDate") LocalDate currentDate);
    
    /**
     * Find active prices for a specific car model
     */
    @Query("SELECT cp FROM CarPrice cp WHERE cp.carModel.id = :carModelId AND " +
           "cp.effectiveDate <= :currentDate AND " +
           "(cp.expiryDate IS NULL OR cp.expiryDate >= :currentDate)")
    List<CarPrice> findActivePricesByCarModel(@Param("carModelId") Long carModelId, 
                                              @Param("currentDate") LocalDate currentDate);
    
    /**
     * Find the latest price for a car model by price type
     */
    @Query("SELECT cp FROM CarPrice cp WHERE cp.carModel.id = :carModelId AND " +
           "cp.priceType = :priceType ORDER BY cp.effectiveDate DESC")
    List<CarPrice> findLatestPriceByCarModelAndType(@Param("carModelId") Long carModelId, 
                                                    @Param("priceType") String priceType);
    
    /**
     * Find the current active price for a car model by price type
     */
    @Query("SELECT cp FROM CarPrice cp WHERE cp.carModel.id = :carModelId AND " +
           "cp.priceType = :priceType AND cp.effectiveDate <= :currentDate AND " +
           "(cp.expiryDate IS NULL OR cp.expiryDate >= :currentDate) " +
           "ORDER BY cp.effectiveDate DESC")
    Optional<CarPrice> findCurrentPriceByCarModelAndType(@Param("carModelId") Long carModelId, 
                                                         @Param("priceType") String priceType,
                                                         @Param("currentDate") LocalDate currentDate);
    
    /**
     * Find prices by effective date range
     */
    List<CarPrice> findByEffectiveDateBetween(LocalDate startDate, LocalDate endDate);
    
    /**
     * Get average price by car make
     */
    @Query("SELECT AVG(cp.price) FROM CarPrice cp JOIN cp.carModel cm WHERE " +
           "LOWER(cm.make) = LOWER(:make) AND cp.effectiveDate <= :currentDate AND " +
           "(cp.expiryDate IS NULL OR cp.expiryDate >= :currentDate)")
    Optional<BigDecimal> getAveragePriceByMake(@Param("make") String make, 
                                               @Param("currentDate") LocalDate currentDate);
    
    /**
     * Count prices by price type
     */
    long countByPriceType(String priceType);
}