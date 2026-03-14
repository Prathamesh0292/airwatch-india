CREATE TABLE cities (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(100) NOT NULL UNIQUE,
    state           VARCHAR(100),
    waqi_station_id VARCHAR(100),
    latitude        DOUBLE,
    longitude       DOUBLE,
    is_active       BOOLEAN DEFAULT TRUE
);

CREATE TABLE users (
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    email    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role     ENUM('USER','ADMIN') DEFAULT 'USER'
);

CREATE TABLE aqi_readings (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    city_id     BIGINT NOT NULL,
    recorded_at DATETIME NOT NULL,
    aqi         INT,
    pm25        DOUBLE,
    pm10        DOUBLE,
    co          DOUBLE,
    humidity    DOUBLE,
    temperature DOUBLE,
    category    VARCHAR(30),
    INDEX       idx_city_time (city_id, recorded_at),
    FOREIGN KEY (city_id) REFERENCES cities(id)
);

CREATE TABLE alert_subscriptions (
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    email     VARCHAR(255) NOT NULL,
    city_id   BIGINT NOT NULL,
    threshold INT DEFAULT 150,
    is_active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (city_id) REFERENCES cities(id)
);

-- Seed cities
INSERT INTO cities (name, state, waqi_station_id, latitude, longitude) VALUES
('Nagpur',    'Maharashtra', 'nagpur',    21.1458, 79.0882),
('Mumbai',    'Maharashtra', 'mumbai',    19.0760, 72.8777),
('Delhi',     'Delhi',       'delhi',     28.6139, 77.2090),
('Pune',      'Maharashtra', 'pune',      18.5204, 73.8567),
('Bangalore', 'Karnataka',   'bangalore', 12.9716, 77.5946),
('Chennai',   'Tamil Nadu',  'chennai',   13.0827, 80.2707),
('Hyderabad', 'Telangana',   'hyderabad', 17.3850, 78.4867),
('Kolkata',   'West Bengal', 'kolkata',   22.5726, 88.3639);