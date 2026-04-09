package com.example.gourmet.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GeocodingService {

    private static final Logger logger = LoggerFactory.getLogger(GeocodingService.class);
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public GeocodingService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public double[] getCoordinates(String address) {
        try {
            // Nominatim API requires User-Agent
            String url = "https://nominatim.openstreetmap.org/search?q=" + address + "&format=json&limit=1";
            
            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "GourmetMap/1.0");
            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
            
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                JsonNode root = objectMapper.readTree(response.getBody());
                if (root.isArray() && root.size() > 0) {
                    JsonNode firstResult = root.get(0);
                    double lat = firstResult.get("lat").asDouble();
                    double lon = firstResult.get("lon").asDouble();
                    return new double[]{lat, lon};
                }
            }
        } catch (Exception e) {
            logger.error("Failed to fetch coordinates for address: " + address, e);
        }
        return null;
    }
}
