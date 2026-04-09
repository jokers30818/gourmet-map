CREATE TABLE IF NOT EXISTS shops (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    area VARCHAR(50) NOT NULL,
    category VARCHAR(50) NOT NULL,
    rating DOUBLE NOT NULL,
    budget VARCHAR(50) NOT NULL,
    hideaway_level VARCHAR(50),
    recommended_menu VARCHAR(100),
    comment VARCHAR(500) NOT NULL,
    image_url1 VARCHAR(500),
    image_url2 VARCHAR(500),
    image_url3 VARCHAR(500),
    image_url4 VARCHAR(500),
    image_url5 VARCHAR(500),
    reaction_count INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
