package com.dealership.api.controller;

import com.dealership.api.entity.CarModel;
import com.dealership.api.entity.CarCategory;
import com.dealership.api.service.CarModelService;
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
 * REST Controller for car model operations
 */
@RestController
@RequestMapping("/api/car-models")
@CrossOrigin(origins = "*")
@Tag(name = "Car Model Management", description = "APIs for managing car models and inventory")
public class CarModelController {
    
    private final CarModelService carModelService;
    
    public CarModelController(CarModelService carModelService) {
        this.carModelService = carModelService;
    }
    
    /**
     * GET /api/car-models - Get all car models
     */
    @GetMapping
    @Operation(summary = "Get all car models", 
               description = "Retrieve a list of all car models in the system")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved car models",
                content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = CarModel.class)))
    public ResponseEntity<List<CarModel>> getAllCarModels() {
        List<CarModel> carModels = carModelService.getAllCarModels();
        return ResponseEntity.ok(carModels);
    }
    
    /**
     * GET /api/car-models/{id} - Get car model by ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get car model by ID", 
               description = "Retrieve a specific car model by its unique identifier")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Car model found",
                    content = @Content(mediaType = "application/json",
                                     schema = @Schema(implementation = CarModel.class))),
        @ApiResponse(responseCode = "404", description = "Car model not found")
    })
    public ResponseEntity<CarModel> getCarModelById(
            @Parameter(description = "Car model ID", required = true)
            @PathVariable Long id) {
        return carModelService.getCarModelById(id)
                .map(carModel -> ResponseEntity.ok(carModel))
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * GET /api/car-models/search/make/{make} - Get car models by make
     */
    @GetMapping("/search/make/{make}")
    @Operation(summary = "Get car models by make", 
               description = "Retrieve all car models from a specific manufacturer")
    @ApiResponse(responseCode = "200", description = "Car models found",
                content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = CarModel.class)))
    public ResponseEntity<List<CarModel>> getCarModelsByMake(
            @Parameter(description = "Car make (e.g., 'Toyota', 'BMW')", required = true)
            @PathVariable String make) {
        List<CarModel> carModels = carModelService.getCarModelsByMake(make);
        return ResponseEntity.ok(carModels);
    }
    
    /**
     * GET /api/car-models/search/category/{category} - Get car models by category
     */
    @GetMapping("/search/category/{category}")
    @Operation(summary = "Get car models by category", 
               description = "Find car models in a specific category")
    @ApiResponse(responseCode = "200", description = "Car models found",
                content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = CarModel.class)))
    public ResponseEntity<List<CarModel>> getCarModelsByCategory(
            @Parameter(description = "Category (SEDAN, SUV, TRUCK, COUPE, HATCHBACK, CONVERTIBLE)", required = true)
            @PathVariable CarCategory category) {
        List<CarModel> carModels = carModelService.getCarModelsByCategory(category);
        return ResponseEntity.ok(carModels);
    }
    
    /**
     * GET /api/car-models/search/year/{year} - Get car models by year
     */
    @GetMapping("/search/year/{year}")
    @Operation(summary = "Get car models by year", 
               description = "Retrieve all car models from a specific model year")
    @ApiResponse(responseCode = "200", description = "Car models found",
                content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = CarModel.class)))
    public ResponseEntity<List<CarModel>> getCarModelsByYear(
            @Parameter(description = "Model year (e.g., 2023, 2024)", required = true)
            @PathVariable Integer year) {
        List<CarModel> carModels = carModelService.getCarModelsByYear(year);
        return ResponseEntity.ok(carModels);
    }
    
    /**
     * GET /api/car-models/search/year-range - Get car models by year range
     */
    @GetMapping("/search/year-range")
    @Operation(summary = "Get car models by year range", 
               description = "Find car models within a specific year range")
    @ApiResponse(responseCode = "200", description = "Car models found",
                content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = CarModel.class)))
    public ResponseEntity<List<CarModel>> getCarModelsByYearRange(
            @Parameter(description = "Starting year (inclusive)", required = true)
            @RequestParam Integer startYear, 
            @Parameter(description = "Ending year (inclusive)", required = true)
            @RequestParam Integer endYear) {
        List<CarModel> carModels = carModelService.getCarModelsByYearRange(startYear, endYear);
        return ResponseEntity.ok(carModels);
    }
    
    /**
     * GET /api/car-models/search/make-and-model - Get car models by make and model
     */
    @GetMapping("/search/make-and-model")
    @Operation(summary = "Search car models by make and model", 
               description = "Find car models matching both make and model name")
    @ApiResponse(responseCode = "200", description = "Car models found",
                content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = CarModel.class)))
    public ResponseEntity<List<CarModel>> getCarModelsByMakeAndModel(
            @Parameter(description = "Car make", required = true)
            @RequestParam String make, 
            @Parameter(description = "Model name", required = true)
            @RequestParam String model) {
        List<CarModel> carModels = carModelService.getCarModelsByMakeAndModel(make, model);
        return ResponseEntity.ok(carModels);
    }
    
    /**
     * GET /api/car-models/dealership/{dealershipId} - Get car models by dealership
     */
    @GetMapping("/dealership/{dealershipId}")
    @Operation(summary = "Get car models by dealership", 
               description = "Retrieve all car models available at a specific dealership")
    @ApiResponse(responseCode = "200", description = "Car models found",
                content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = CarModel.class)))
    public ResponseEntity<List<CarModel>> getCarModelsByDealership(
            @Parameter(description = "Dealership ID", required = true)
            @PathVariable Long dealershipId) {
        List<CarModel> carModels = carModelService.getCarModelsByDealership(dealershipId);
        return ResponseEntity.ok(carModels);
    }
    
    /**
     * GET /api/car-models/search/make-and-category - Get car models by make and category
     */
    @GetMapping("/search/make-and-category")
    @Operation(summary = "Search car models by make and category", 
               description = "Find car models matching both make and category")
    @ApiResponse(responseCode = "200", description = "Car models found",
                content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = CarModel.class)))
    public ResponseEntity<List<CarModel>> getCarModelsByMakeAndCategory(
            @Parameter(description = "Car make", required = true)
            @RequestParam String make, 
            @Parameter(description = "Car category", required = true)
            @RequestParam CarCategory category) {
        List<CarModel> carModels = carModelService.getCarModelsByMakeAndCategory(make, category);
        return ResponseEntity.ok(carModels);
    }
    
    /**
     * GET /api/car-models/latest - Get latest car models by make
     */
    @GetMapping("/latest")
    @Operation(summary = "Get latest car models", 
               description = "Retrieve the newest car models from each make")
    @ApiResponse(responseCode = "200", description = "Latest car models found",
                content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = CarModel.class)))
    public ResponseEntity<List<CarModel>> getLatestCarModelsByMake() {
        List<CarModel> carModels = carModelService.getLatestCarModelsByMake();
        return ResponseEntity.ok(carModels);
    }
    
    /**
     * GET /api/car-models/search/description - Search car models by description
     */
    @GetMapping("/search/description")
    @Operation(summary = "Search car models by description", 
               description = "Find car models containing specific keywords in their description")
    @ApiResponse(responseCode = "200", description = "Car models found",
                content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = CarModel.class)))
    public ResponseEntity<List<CarModel>> searchCarModelsByDescription(
            @Parameter(description = "Keyword to search in descriptions", required = true)
            @RequestParam String keyword) {
        List<CarModel> carModels = carModelService.searchCarModelsByDescription(keyword);
        return ResponseEntity.ok(carModels);
    }
    
    /**
     * GET /api/car-models/search/location - Get car models by dealership location
     */
    @GetMapping("/search/location")
    @Operation(summary = "Get car models by dealership location", 
               description = "Find car models available in dealerships at a specific location")
    @ApiResponse(responseCode = "200", description = "Car models found",
                content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = CarModel.class)))
    public ResponseEntity<List<CarModel>> getCarModelsByDealershipLocation(
            @Parameter(description = "Location to search", required = true)
            @RequestParam String location) {
        List<CarModel> carModels = carModelService.getCarModelsByDealershipLocation(location);
        return ResponseEntity.ok(carModels);
    }
    
    /**
     * POST /api/car-models - Create a new car model
     */
    @PostMapping
    @Operation(summary = "Create a new car model", 
               description = "Add a new car model to the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Car model created successfully",
                    content = @Content(mediaType = "application/json",
                                     schema = @Schema(implementation = CarModel.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<CarModel> createCarModel(
            @Parameter(description = "Car model details", required = true)
            @Valid @RequestBody CarModel carModel) {
        CarModel createdCarModel = carModelService.createCarModel(carModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCarModel);
    }
    
    /**
     * PUT /api/car-models/{id} - Update an existing car model
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update a car model", 
               description = "Update the details of an existing car model")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Car model updated successfully",
                    content = @Content(mediaType = "application/json",
                                     schema = @Schema(implementation = CarModel.class))),
        @ApiResponse(responseCode = "404", description = "Car model not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<CarModel> updateCarModel(
            @Parameter(description = "Car model ID", required = true)
            @PathVariable Long id, 
            @Parameter(description = "Updated car model details", required = true)
            @Valid @RequestBody CarModel carModelDetails) {
        try {
            CarModel updatedCarModel = carModelService.updateCarModel(id, carModelDetails);
            return ResponseEntity.ok(updatedCarModel);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * DELETE /api/car-models/{id} - Delete a car model
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a car model", 
               description = "Remove a car model from the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Car model deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Car model not found")
    })
    public ResponseEntity<Void> deleteCarModel(
            @Parameter(description = "Car model ID", required = true)
            @PathVariable Long id) {
        try {
            carModelService.deleteCarModel(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * GET /api/car-models/count/category/{category} - Count car models by category
     */
    @GetMapping("/count/category/{category}")
    @Operation(summary = "Count car models by category", 
               description = "Get the number of car models in a specific category")
    @ApiResponse(responseCode = "200", description = "Count retrieved successfully",
                content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = Long.class)))
    public ResponseEntity<Long> countCarModelsByCategory(
            @Parameter(description = "Car category to count", required = true)
            @PathVariable CarCategory category) {
        long count = carModelService.countCarModelsByCategory(category);
        return ResponseEntity.ok(count);
    }
}