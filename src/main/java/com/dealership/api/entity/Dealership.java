package com.dealership.api.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a car dealership
 */
@Entity
@Table(name = "dealerships")
public class Dealership {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Dealership name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Column(nullable = false)
    private String name;
    
    @NotBlank(message = "Location is required")
    @Size(min = 2, max = 200, message = "Location must be between 2 and 200 characters")
    @Column(nullable = false)
    private String location;
    
    @Size(max = 15, message = "Phone number cannot exceed 15 characters")
    private String phoneNumber;
    
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    private String email;
    
    @OneToMany(mappedBy = "dealership", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("dealership-carmodels")
    private List<CarModel> carModels = new ArrayList<>();
    
    // Constructors
    public Dealership() {}
    
    public Dealership(String name, String location) {
        this.name = name;
        this.location = location;
    }
    
    public Dealership(String name, String location, String phoneNumber, String email) {
        this.name = name;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public List<CarModel> getCarModels() {
        return carModels;
    }
    
    public void setCarModels(List<CarModel> carModels) {
        this.carModels = carModels;
    }
    
    // Utility methods
    public void addCarModel(CarModel carModel) {
        carModels.add(carModel);
        carModel.setDealership(this);
    }
    
    public void removeCarModel(CarModel carModel) {
        carModels.remove(carModel);
        carModel.setDealership(null);
    }
    
    @Override
    public String toString() {
        return "Dealership{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}