package com.dealership.api.controller;

import com.dealership.api.entity.Dealership;
import com.dealership.api.service.DealershipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * REST Controller for dealership operations
 */
@RestController
@RequestMapping("/api/dealerships")
@CrossOrigin(origins = "*")
@Tag(name = "Dealership Management", description = "APIs for managing car dealerships")
public class DealershipController {
    
    private final DealershipService dealershipService;
    
    public DealershipController(DealershipService dealershipService) {
        this.dealershipService = dealershipService;
    }
    
    /**
     * GET /api/dealerships - Get all dealerships
     */
    @GetMapping
    @Operation(summary = "Get all dealerships", 
               description = "Retrieve a list of all registered dealerships")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved dealerships",
                    content = @Content(mediaType = "application/json",
                                     schema = @Schema(implementation = Dealership.class)))
    })
    public ResponseEntity<List<Dealership>> getAllDealerships() {
        List<Dealership> dealerships = dealershipService.getAllDealerships();
        return ResponseEntity.ok(dealerships);
    }
    
    /**
     * GET /api/dealerships/{id} - Get dealership by ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get dealership by ID", 
               description = "Retrieve a specific dealership by its unique identifier")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Dealership found",
                    content = @Content(mediaType = "application/json",
                                     schema = @Schema(implementation = Dealership.class))),
        @ApiResponse(responseCode = "404", description = "Dealership not found")
    })
    public ResponseEntity<Dealership> getDealershipById(
            @Parameter(description = "Dealership ID", required = true)
            @PathVariable Long id) {
        return dealershipService.getDealershipById(id)
                .map(dealership -> ResponseEntity.ok(dealership))
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * GET /api/dealerships/search/name/{name} - Get dealership by name
     */
    @GetMapping("/search/name/{name}")
    @Operation(summary = "Get dealership by name", 
               description = "Find a dealership by its name (case-insensitive)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Dealership found",
                    content = @Content(mediaType = "application/json",
                                     schema = @Schema(implementation = Dealership.class))),
        @ApiResponse(responseCode = "404", description = "Dealership not found")
    })
    public ResponseEntity<Dealership> getDealershipByName(
            @Parameter(description = "Dealership name", required = true)
            @PathVariable String name) {
        return dealershipService.getDealershipByName(name)
                .map(dealership -> ResponseEntity.ok(dealership))
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * GET /api/dealerships/search/location - Get dealerships by location
     */
    @GetMapping("/search/location")
    @Operation(summary = "Search dealerships by location", 
               description = "Find dealerships in a specific location (partial match supported)")
    @ApiResponse(responseCode = "200", description = "Dealerships found",
                content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = Dealership.class)))
    public ResponseEntity<List<Dealership>> getDealershipsByLocation(
            @Parameter(description = "Location to search (e.g., 'New York', 'CA')", required = true)
            @RequestParam String location) {
        List<Dealership> dealerships = dealershipService.getDealershipsByLocation(location);
        return ResponseEntity.ok(dealerships);
    }
    
    /**
     * GET /api/dealerships/with-cars - Get dealerships with car models
     */
    @GetMapping("/with-cars")
    @Operation(summary = "Get dealerships with car inventory", 
               description = "Retrieve only dealerships that have car models in stock")
    @ApiResponse(responseCode = "200", description = "Dealerships with car inventory",
                content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = Dealership.class)))
    public ResponseEntity<List<Dealership>> getDealershipsWithCarModels() {
        List<Dealership> dealerships = dealershipService.getDealershipsWithCarModels();
        return ResponseEntity.ok(dealerships);
    }
    
    /**
     * GET /api/dealerships/search/location-and-make - Get dealerships by location and car make
     */
    @GetMapping("/search/location-and-make")
    @Operation(summary = "Search dealerships by location and car make", 
               description = "Find dealerships in a specific location that carry a particular car make")
    @ApiResponse(responseCode = "200", description = "Matching dealerships found",
                content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = Dealership.class)))
    public ResponseEntity<List<Dealership>> getDealershipsByLocationAndCarMake(
            @Parameter(description = "Location to search", required = true)
            @RequestParam String location, 
            @Parameter(description = "Car make (e.g., 'Toyota', 'BMW')", required = true)
            @RequestParam String make) {
        List<Dealership> dealerships = dealershipService.getDealershipsByLocationAndCarMake(location, make);
        return ResponseEntity.ok(dealerships);
    }
    
    /**
     * POST /api/dealerships - Create a new dealership
     */
    @PostMapping
    @Operation(summary = "Create a new dealership", 
               description = "Register a new dealership in the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Dealership created successfully",
                    content = @Content(mediaType = "application/json",
                                     schema = @Schema(implementation = Dealership.class))),
        @ApiResponse(responseCode = "409", description = "Dealership with this name already exists"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<Dealership> createDealership(
            @Parameter(description = "Dealership details", required = true)
            @Valid @RequestBody Dealership dealership) {
        try {
            Dealership createdDealership = dealershipService.createDealership(dealership);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDealership);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    
    /**
     * PUT /api/dealerships/{id} - Update an existing dealership
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update a dealership", 
               description = "Update the details of an existing dealership")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Dealership updated successfully",
                    content = @Content(mediaType = "application/json",
                                     schema = @Schema(implementation = Dealership.class))),
        @ApiResponse(responseCode = "404", description = "Dealership not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<Dealership> updateDealership(
            @Parameter(description = "Dealership ID", required = true)
            @PathVariable Long id, 
            @Parameter(description = "Updated dealership details", required = true)
            @Valid @RequestBody Dealership dealershipDetails) {
        try {
            Dealership updatedDealership = dealershipService.updateDealership(id, dealershipDetails);
            return ResponseEntity.ok(updatedDealership);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * DELETE /api/dealerships/{id} - Delete a dealership
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a dealership", 
               description = "Remove a dealership from the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Dealership deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Dealership not found")
    })
    public ResponseEntity<Void> deleteDealership(
            @Parameter(description = "Dealership ID", required = true)
            @PathVariable Long id) {
        try {
            dealershipService.deleteDealership(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * GET /api/dealerships/count/location - Count dealerships by location
     */
    @GetMapping("/count/location")
    @Operation(summary = "Count dealerships by location", 
               description = "Get the number of dealerships in a specific location")
    @ApiResponse(responseCode = "200", description = "Count retrieved successfully",
                content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = Long.class)))
    public ResponseEntity<Long> countDealershipsByLocation(
            @Parameter(description = "Location to count", required = true)
            @RequestParam String location) {
        long count = dealershipService.countDealershipsByLocation(location);
        return ResponseEntity.ok(count);
    }
}