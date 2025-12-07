-- Sample data for E-Dealership API

-- Insert sample dealerships
INSERT INTO dealerships (name, location, phone_number, email) VALUES ('AutoMax Downtown', 'New York, NY', '555-0101', 'info@automax-ny.com');
INSERT INTO dealerships (name, location, phone_number, email) VALUES ('Premium Motors', 'Los Angeles, CA', '555-0202', 'sales@premiummotors-la.com');
INSERT INTO dealerships (name, location, phone_number, email) VALUES ('City Car Center', 'Chicago, IL', '555-0303', 'contact@citycar-chicago.com');
INSERT INTO dealerships (name, location, phone_number, email) VALUES ('Luxury Auto Gallery', 'Miami, FL', '555-0404', 'gallery@luxuryauto-miami.com');
INSERT INTO dealerships (name, location, phone_number, email) VALUES ('Metro Vehicle Hub', 'Seattle, WA', '555-0505', 'hub@metrovehicle-seattle.com');

-- Insert sample car models
-- AutoMax Downtown (ID: 1) - Toyota models
INSERT INTO car_models (make, model, model_year, category, description, dealership_id) VALUES ('Toyota', 'Camry', 2024, 'SEDAN', 'Reliable mid-size sedan with excellent fuel economy', 1);
INSERT INTO car_models (make, model, model_year, category, description, dealership_id) VALUES ('Toyota', 'RAV4', 2024, 'SUV', 'Compact SUV perfect for urban and outdoor adventures', 1);
INSERT INTO car_models (make, model, model_year, category, description, dealership_id) VALUES ('Toyota', 'Prius', 2024, 'HYBRID', 'Leading hybrid technology with outstanding fuel efficiency', 1);
INSERT INTO car_models (make, model, model_year, category, description, dealership_id) VALUES ('Toyota', 'Corolla', 2023, 'SEDAN', 'Compact sedan ideal for city driving', 1);

-- Premium Motors (ID: 2) - Luxury brands
INSERT INTO car_models (make, model, model_year, category, description, dealership_id) VALUES ('BMW', 'X5', 2024, 'SUV', 'Luxury SUV with premium features and performance', 2);
INSERT INTO car_models (make, model, model_year, category, description, dealership_id) VALUES ('Mercedes-Benz', 'C-Class', 2024, 'SEDAN', 'Elegant luxury sedan with advanced technology', 2);
INSERT INTO car_models (make, model, model_year, category, description, dealership_id) VALUES ('Audi', 'Q7', 2024, 'SUV', 'Spacious luxury SUV with cutting-edge features', 2);
INSERT INTO car_models (make, model, model_year, category, description, dealership_id) VALUES ('BMW', '3 Series', 2023, 'SEDAN', 'Sport luxury sedan with dynamic performance', 2);

-- City Car Center (ID: 3) - Popular brands
INSERT INTO car_models (make, model, model_year, category, description, dealership_id) VALUES ('Honda', 'Civic', 2024, 'SEDAN', 'Sporty and efficient compact sedan', 3);
INSERT INTO car_models (make, model, model_year, category, description, dealership_id) VALUES ('Honda', 'CR-V', 2024, 'SUV', 'Versatile compact SUV with spacious interior', 3);
INSERT INTO car_models (make, model, model_year, category, description, dealership_id) VALUES ('Nissan', 'Altima', 2024, 'SEDAN', 'Comfortable mid-size sedan with innovative features', 3);
INSERT INTO car_models (make, model, model_year, category, description, dealership_id) VALUES ('Ford', 'Escape', 2023, 'SUV', 'Smart compact SUV with advanced safety features', 3);

-- Luxury Auto Gallery (ID: 4) - High-end vehicles
INSERT INTO car_models (make, model, model_year, category, description, dealership_id) VALUES ('Porsche', '911', 2024, 'COUPE', 'Iconic sports car with legendary performance', 4);
INSERT INTO car_models (make, model, model_year, category, description, dealership_id) VALUES ('Lexus', 'ES', 2024, 'SEDAN', 'Luxury sedan with refined comfort and reliability', 4);
INSERT INTO car_models (make, model, model_year, category, description, dealership_id) VALUES ('Tesla', 'Model S', 2024, 'ELECTRIC', 'Premium electric sedan with cutting-edge technology', 4);
INSERT INTO car_models (make, model, model_year, category, description, dealership_id) VALUES ('Mercedes-Benz', 'GLE', 2024, 'SUV', 'Luxury SUV combining comfort with capability', 4);

-- Metro Vehicle Hub (ID: 5) - Diverse selection
INSERT INTO car_models (make, model, model_year, category, description, dealership_id) VALUES ('Chevrolet', 'Silverado', 2024, 'TRUCK', 'Full-size pickup truck built for work and play', 5);
INSERT INTO car_models (make, model, model_year, category, description, dealership_id) VALUES ('Jeep', 'Wrangler', 2024, 'SUV', 'Legendary off-road capability with modern amenities', 5);
INSERT INTO car_models (make, model, model_year, category, description, dealership_id) VALUES ('Hyundai', 'Elantra', 2024, 'SEDAN', 'Stylish compact sedan with generous warranty', 5);
INSERT INTO car_models (make, model, model_year, category, description, dealership_id) VALUES ('Mazda', 'CX-5', 2023, 'SUV', 'Compact SUV with premium feel and driving dynamics', 5);

-- Insert sample car prices (just a subset for demonstration)
-- Toyota Camry (ID: 1)
INSERT INTO car_prices (price, price_type, effective_date, notes, car_model_id) VALUES (28500.00, 'MSRP', '2024-01-01', 'Starting price for base trim', 1);
INSERT INTO car_prices (price, price_type, effective_date, notes, car_model_id) VALUES (26800.00, 'Invoice', '2024-01-01', 'Dealer invoice price', 1);

-- BMW X5 (ID: 5)
INSERT INTO car_prices (price, price_type, effective_date, notes, car_model_id) VALUES (65700.00, 'MSRP', '2024-01-01', 'Starting price for xDrive40i', 5);
INSERT INTO car_prices (price, price_type, effective_date, notes, car_model_id) VALUES (62000.00, 'Invoice', '2024-01-01', 'Dealer invoice price', 5);

-- Honda Civic (ID: 9)
INSERT INTO car_prices (price, price_type, effective_date, notes, car_model_id) VALUES (24200.00, 'MSRP', '2024-01-01', 'Starting price for LX', 9);
INSERT INTO car_prices (price, price_type, effective_date, notes, car_model_id) VALUES (22900.00, 'Invoice', '2024-01-01', 'Dealer invoice price', 9);

-- Porsche 911 (ID: 13)
INSERT INTO car_prices (price, price_type, effective_date, notes, car_model_id) VALUES (115000.00, 'MSRP', '2024-01-01', 'Starting price for Carrera', 13);
INSERT INTO car_prices (price, price_type, effective_date, notes, car_model_id) VALUES (108000.00, 'Invoice', '2024-01-01', 'Dealer invoice price', 13);

-- Tesla Model S (ID: 15)
INSERT INTO car_prices (price, price_type, effective_date, notes, car_model_id) VALUES (94990.00, 'MSRP', '2024-01-01', 'Direct sales price', 15);