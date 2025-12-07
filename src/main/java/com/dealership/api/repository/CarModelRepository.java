package com.dealership.api.repository;

import com.dealership.api.entity.CarModel;
import com.dealership.api.entity.CarCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for CarModel entity
 */
@Repository
public interface CarModelRepository extends JpaRepository<CarModel, Long> {
    
    /**
     * Find car models by make (case-insensitive)
     */
    List<CarModel> findByMakeIgnoreCase(String make);
    
    /**
     * Find car models by category
     */
    List<CarModel> findByCategory(CarCategory category);
    
    /**
     * Find car models by year
     */
    List<CarModel> findByYear(Integer year);
    
    /**
     * Find car models by year range
     */
    List<CarModel> findByYearBetween(Integer startYear, Integer endYear);
    
    /**
     * Find car models by make and model name
     */
    List<CarModel> findByMakeIgnoreCaseAndModelIgnoreCase(String make, String model);
    
    /**
     * Find car models by dealership ID
     */
    List<CarModel> findByDealershipId(Long dealershipId);
    
    /**
     * Find car models by make and category
     */
    List<CarModel> findByMakeIgnoreCaseAndCategory(String make, CarCategory category);
    
    /**
     * Find the latest car models (most recent years) for each make
     */
    @Query("SELECT cm FROM CarModel cm WHERE cm.year = " +
           "(SELECT MAX(cm2.year) FROM CarModel cm2 WHERE cm2.make = cm.make)")
    List<CarModel> findLatestModelsByMake();
    
    /**
     * Find car models with description containing specified text
     */
    List<CarModel> findByDescriptionContainingIgnoreCase(String keyword);
    
    /**
     * Count car models by category
     */
    long countByCategory(CarCategory category);
    
    /**
     * Find car models by dealership location
     */
    @Query("SELECT cm FROM CarModel cm JOIN cm.dealership d WHERE " +
           "LOWER(d.location) LIKE LOWER(CONCAT('%', :location, '%'))")
    List<CarModel> findByDealershipLocation(@Param("location") String location);
}