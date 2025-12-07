package com.dealership.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entity representing car pricing information
 */
@Entity
@Table(name = "car_prices")
public class CarPrice {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    
    @Size(max = 50, message = "Price type cannot exceed 50 characters")
    private String priceType = "MSRP"; // MSRP, Invoice, Market, etc.
    
    @NotNull(message = "Effective date is required")
    @Column(nullable = false)
    private LocalDate effectiveDate;
    
    private LocalDate expiryDate;
    
    @Size(max = 200, message = "Notes cannot exceed 200 characters")
    private String notes;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_model_id", nullable = false)
    @JsonBackReference("carmodel-carprices")
    private CarModel carModel;
    
    // Constructors
    public CarPrice() {}
    
    public CarPrice(BigDecimal price, LocalDate effectiveDate) {
        this.price = price;
        this.effectiveDate = effectiveDate;
    }
    
    public CarPrice(BigDecimal price, String priceType, LocalDate effectiveDate) {
        this.price = price;
        this.priceType = priceType;
        this.effectiveDate = effectiveDate;
    }
    
    public CarPrice(BigDecimal price, String priceType, LocalDate effectiveDate, LocalDate expiryDate, String notes) {
        this.price = price;
        this.priceType = priceType;
        this.effectiveDate = effectiveDate;
        this.expiryDate = expiryDate;
        this.notes = notes;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public String getPriceType() {
        return priceType;
    }
    
    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }
    
    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }
    
    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
    
    public LocalDate getExpiryDate() {
        return expiryDate;
    }
    
    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public CarModel getCarModel() {
        return carModel;
    }
    
    public void setCarModel(CarModel carModel) {
        this.carModel = carModel;
    }
    
    @Override
    public String toString() {
        return "CarPrice{" +
                "id=" + id +
                ", price=" + price +
                ", priceType='" + priceType + '\'' +
                ", effectiveDate=" + effectiveDate +
                ", expiryDate=" + expiryDate +
                ", notes='" + notes + '\'' +
                '}';
    }
}