package com.dealership.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a car model
 */
@Entity
@Table(name = "car_models")
public class CarModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Make is required")
    @Size(min = 1, max = 50, message = "Make must be between 1 and 50 characters")
    @Column(nullable = false)
    private String make;
    
    @NotBlank(message = "Model is required")
    @Size(min = 1, max = 50, message = "Model must be between 1 and 50 characters")
    @Column(nullable = false)
    private String model;
    
    @NotNull(message = "Year is required")
    @Min(value = 1900, message = "Year must be 1900 or later")
    @Column(name = "model_year", nullable = false)
    private Integer year;
    
    @NotNull(message = "Category is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CarCategory category;
    
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dealership_id", nullable = false)
    @JsonBackReference("dealership-carmodels")
    private Dealership dealership;
    
    @OneToMany(mappedBy = "carModel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("carmodel-carprices")
    private List<CarPrice> carPrices = new ArrayList<>();
    
    // Constructors
    public CarModel() {}
    
    public CarModel(String make, String model, Integer year, CarCategory category) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.category = category;
    }
    
    public CarModel(String make, String model, Integer year, CarCategory category, String description) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.category = category;
        this.description = description;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getMake() {
        return make;
    }
    
    public void setMake(String make) {
        this.make = make;
    }
    
    public String getModel() {
        return model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    
    public Integer getYear() {
        return year;
    }
    
    public void setYear(Integer year) {
        this.year = year;
    }
    
    public CarCategory getCategory() {
        return category;
    }
    
    public void setCategory(CarCategory category) {
        this.category = category;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Dealership getDealership() {
        return dealership;
    }
    
    public void setDealership(Dealership dealership) {
        this.dealership = dealership;
    }
    
    public List<CarPrice> getCarPrices() {
        return carPrices;
    }
    
    public void setCarPrices(List<CarPrice> carPrices) {
        this.carPrices = carPrices;
    }
    
    // Utility methods
    public void addCarPrice(CarPrice carPrice) {
        carPrices.add(carPrice);
        carPrice.setCarModel(this);
    }
    
    public void removeCarPrice(CarPrice carPrice) {
        carPrices.remove(carPrice);
        carPrice.setCarModel(null);
    }
    
    @Override
    public String toString() {
        return "CarModel{" +
                "id=" + id +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", category=" + category +
                ", description='" + description + '\'' +
                '}';
    }
}