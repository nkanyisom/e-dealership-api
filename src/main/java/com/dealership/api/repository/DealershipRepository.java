package com.dealership.api.repository;

import com.dealership.api.entity.Dealership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Dealership entity
 */
@Repository
public interface DealershipRepository extends JpaRepository<Dealership, Long> {
    
    /**
     * Find dealership by name (case-insensitive)
     */
    Optional<Dealership> findByNameIgnoreCase(String name);
    
    /**
     * Find dealerships by location containing the specified text
     */
    List<Dealership> findByLocationContainingIgnoreCase(String location);
    
    /**
     * Find dealerships by email
     */
    Optional<Dealership> findByEmail(String email);
    
    /**
     * Find dealerships that have car models in stock
     */
    @Query("SELECT DISTINCT d FROM Dealership d WHERE SIZE(d.carModels) > 0")
    List<Dealership> findDealershipsWithCarModels();
    
    /**
     * Find dealerships by location and having specific car make
     */
    @Query("SELECT DISTINCT d FROM Dealership d JOIN d.carModels cm WHERE " +
           "LOWER(d.location) LIKE LOWER(CONCAT('%', :location, '%')) AND " +
           "LOWER(cm.make) = LOWER(:make)")
    List<Dealership> findByLocationAndCarMake(@Param("location") String location, 
                                              @Param("make") String make);
    
    /**
     * Count dealerships by location
     */
    long countByLocationContainingIgnoreCase(String location);
}