package com.dealership.api.controller;

import com.dealership.api.entity.CarPrice;
import com.dealership.api.service.CarPriceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * REST Controller for car price operations
 */
@RestController
@RequestMapping("/api/car-prices")
@CrossOrigin(origins = "*")
@Tag(name = "Car Price Management", description = "APIs for managing car pricing information")
public class CarPriceController {
    
    private final CarPriceService carPriceService;
    
    public CarPriceController(CarPriceService carPriceService) {
        this.carPriceService = carPriceService;
    }
    
    /**
     * GET /api/car-prices - Get all car prices
     */
    @GetMapping
    @Operation(summary = "Get all car prices", 
               description = "Retrieve a list of all car pricing information")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved car prices",
                content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = CarPrice.class)))
    public ResponseEntity<List<CarPrice>> getAllCarPrices() {
        List<CarPrice> carPrices = carPriceService.getAllCarPrices();
        return ResponseEntity.ok(carPrices);
    }
    
    /**
     * GET /api/car-prices/{id} - Get car price by ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get car price by ID", 
               description = "Retrieve a specific car price by its unique identifier")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Car price found",
                    content = @Content(mediaType = "application/json",
                                     schema = @Schema(implementation = CarPrice.class))),
        @ApiResponse(responseCode = "404", description = "Car price not found")
    })
    public ResponseEntity<CarPrice> getCarPriceById(
            @Parameter(description = "Car price ID", required = true)
            @PathVariable Long id) {
        return carPriceService.getCarPriceById(id)
                .map(carPrice -> ResponseEntity.ok(carPrice))
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * GET /api/car-prices/car-model/{carModelId} - Get car prices by car model
     */
    @GetMapping("/car-model/{carModelId}")
    @Operation(summary = "Get car prices by car model", 
               description = "Retrieve all pricing information for a specific car model")
    @ApiResponse(responseCode = "200", description = "Car prices found",
                content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = CarPrice.class)))
    public ResponseEntity<List<CarPrice>> getCarPricesByCarModel(
            @Parameter(description = "Car model ID", required = true)
            @PathVariable Long carModelId) {
        List<CarPrice> carPrices = carPriceService.getCarPricesByCarModel(carModelId);
        return ResponseEntity.ok(carPrices);
    }
    
    /**
     * GET /api/car-prices/search/type/{priceType} - Get car prices by type
     */
    @GetMapping("/search/type/{priceType}")
    @Operation(summary = "Get car prices by type", 
               description = "Find car prices of a specific type (e.g., MSRP, dealer, sale)")
    @ApiResponse(responseCode = "200", description = "Car prices found",
                content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = CarPrice.class)))
    public ResponseEntity<List<CarPrice>> getCarPricesByType(
            @Parameter(description = "Price type (e.g., 'MSRP', 'DEALER', 'SALE')", required = true)
            @PathVariable String priceType) {
        List<CarPrice> carPrices = carPriceService.getCarPricesByType(priceType);
        return ResponseEntity.ok(carPrices);
    }
    
    /**
     * GET /api/car-prices/search/price-range - Get car prices within price range
     */
    @GetMapping("/search/price-range")
    @Operation(summary = "Get car prices by price range", 
               description = "Find car prices within a specified price range")
    @ApiResponse(responseCode = "200", description = "Car prices found",
                content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = CarPrice.class)))
    public ResponseEntity<List<CarPrice>> getCarPricesInRange(
            @Parameter(description = "Minimum price", required = true, example = "25000.00")
            @RequestParam BigDecimal minPrice, 
            @Parameter(description = "Maximum price", required = true, example = "75000.00")
            @RequestParam BigDecimal maxPrice) {
        List<CarPrice> carPrices = carPriceService.getCarPricesInRange(minPrice, maxPrice);
        return ResponseEntity.ok(carPrices);
    }
    
    /**
     * GET /api/car-prices/active - Get currently active car prices
     */
    @GetMapping("/active")
    @Operation(summary = "Get active car prices", 
               description = "Retrieve currently active pricing information")
    @ApiResponse(responseCode = "200", description = "Active car prices found",
                content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = CarPrice.class)))
    public ResponseEntity<List<CarPrice>> getActivePrices() {
        List<CarPrice> carPrices = carPriceService.getActivePrices();
        return ResponseEntity.ok(carPrices);
    }
    
    /**
     * GET /api/car-prices/active/car-model/{carModelId} - Get active prices for a car model
     */
    @GetMapping("/active/car-model/{carModelId}")
    @Operation(summary = "Get active prices for car model", 
               description = "Retrieve currently active prices for a specific car model")
    @ApiResponse(responseCode = "200", description = "Active prices for car model found",
                content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = CarPrice.class)))
    public ResponseEntity<List<CarPrice>> getActivePricesForCarModel(
            @Parameter(description = "Car model ID", required = true)
            @PathVariable Long carModelId) {
        List<CarPrice> carPrices = carPriceService.getActivePricesForCarModel(carModelId);
        return ResponseEntity.ok(carPrices);
    }
    
    /**
     * GET /api/car-prices/latest/car-model/{carModelId}/type/{priceType} - Get latest price by car model and type
     */
    @GetMapping("/latest/car-model/{carModelId}/type/{priceType}")
    @Operation(summary = "Get latest price by car model and type", 
               description = "Retrieve the most recent price for a specific car model and price type")
    @ApiResponse(responseCode = "200", description = "Latest price found",
                content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = CarPrice.class)))
    public ResponseEntity<List<CarPrice>> getLatestPriceByCarModelAndType(
            @Parameter(description = "Car model ID", required = true)
            @PathVariable Long carModelId, 
            @Parameter(description = "Price type", required = true)
            @PathVariable String priceType) {
        List<CarPrice> carPrices = carPriceService.getLatestPriceByCarModelAndType(carModelId, priceType);
        return ResponseEntity.ok(carPrices);
    }
    
    /**
     * GET /api/car-prices/current/car-model/{carModelId}/type/{priceType} - Get current price by car model and type
     */
    @GetMapping("/current/car-model/{carModelId}/type/{priceType}")
    @Operation(summary = "Get current price by car model and type", 
               description = "Retrieve the current active price for a specific car model and price type")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Current price found",
                    content = @Content(mediaType = "application/json",
                                     schema = @Schema(implementation = CarPrice.class))),
        @ApiResponse(responseCode = "404", description = "Current price not found")
    })
    public ResponseEntity<CarPrice> getCurrentPriceByCarModelAndType(
            @Parameter(description = "Car model ID", required = true)
            @PathVariable Long carModelId, 
            @Parameter(description = "Price type", required = true)
            @PathVariable String priceType) {
        return carPriceService.getCurrentPriceByCarModelAndType(carModelId, priceType)
                .map(carPrice -> ResponseEntity.ok(carPrice))
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * GET /api/car-prices/search/date-range - Get prices by date range
     */
    @GetMapping("/search/date-range")
    @Operation(summary = "Get prices by date range", 
               description = "Find car prices within a specific date range")
    @ApiResponse(responseCode = "200", description = "Prices within date range found",
                content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = CarPrice.class)))
    public ResponseEntity<List<CarPrice>> getPricesByDateRange(
            @Parameter(description = "Start date (YYYY-MM-DD)", required = true, example = "2023-01-01")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "End date (YYYY-MM-DD)", required = true, example = "2023-12-31")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<CarPrice> carPrices = carPriceService.getPricesByDateRange(startDate, endDate);
        return ResponseEntity.ok(carPrices);
    }
    
    /**
     * GET /api/car-prices/average/make/{make} - Get average price by car make
     */
    @GetMapping("/average/make/{make}")
    @Operation(summary = "Get average price by car make", 
               description = "Calculate the average price for all models of a specific car make")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Average price calculated",
                    content = @Content(mediaType = "application/json",
                                     schema = @Schema(implementation = BigDecimal.class))),
        @ApiResponse(responseCode = "404", description = "No prices found for the specified make")
    })
    public ResponseEntity<BigDecimal> getAveragePriceByMake(
            @Parameter(description = "Car make", required = true)
            @PathVariable String make) {
        return carPriceService.getAveragePriceByMake(make)
                .map(averagePrice -> ResponseEntity.ok(averagePrice))
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * POST /api/car-prices - Create a new car price
     */
    @PostMapping
    @Operation(summary = "Create a new car price", 
               description = "Add new pricing information for a car model")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Car price created successfully",
                    content = @Content(mediaType = "application/json",
                                     schema = @Schema(implementation = CarPrice.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<CarPrice> createCarPrice(
            @Parameter(description = "Car price details", required = true)
            @Valid @RequestBody CarPrice carPrice) {
        CarPrice createdCarPrice = carPriceService.createCarPrice(carPrice);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCarPrice);
    }
    
    /**
     * PUT /api/car-prices/{id} - Update an existing car price
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update a car price", 
               description = "Update the details of an existing car price")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Car price updated successfully",
                    content = @Content(mediaType = "application/json",
                                     schema = @Schema(implementation = CarPrice.class))),
        @ApiResponse(responseCode = "404", description = "Car price not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<CarPrice> updateCarPrice(
            @Parameter(description = "Car price ID", required = true)
            @PathVariable Long id, 
            @Parameter(description = "Updated car price details", required = true)
            @Valid @RequestBody CarPrice carPriceDetails) {
        try {
            CarPrice updatedCarPrice = carPriceService.updateCarPrice(id, carPriceDetails);
            return ResponseEntity.ok(updatedCarPrice);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * DELETE /api/car-prices/{id} - Delete a car price
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a car price", 
               description = "Remove a car price from the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Car price deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Car price not found")
    })
    public ResponseEntity<Void> deleteCarPrice(
            @Parameter(description = "Car price ID", required = true)
            @PathVariable Long id) {
        try {
            carPriceService.deleteCarPrice(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * GET /api/car-prices/count/type/{priceType} - Count prices by type
     */
    @GetMapping("/count/type/{priceType}")
    @Operation(summary = "Count prices by type", 
               description = "Get the number of price entries for a specific price type")
    @ApiResponse(responseCode = "200", description = "Count retrieved successfully",
                content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = Long.class)))
    public ResponseEntity<Long> countPricesByType(
            @Parameter(description = "Price type to count", required = true)
            @PathVariable String priceType) {
        long count = carPriceService.countPricesByType(priceType);
        return ResponseEntity.ok(count);
    }
}