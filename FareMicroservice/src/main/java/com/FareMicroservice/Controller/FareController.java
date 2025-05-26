package com.FareMicroservice.Controller;

import com.FareMicroservice.Entities.Fare;
import com.FareMicroservice.Service.FareServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/fare")
public class FareController {

    private static final Logger logger = LoggerFactory.getLogger(FareController.class);

    @Autowired
    private FareServiceImpl fareService;

    @PostMapping("/add")
    public ResponseEntity<Fare> addFare(@Valid @RequestBody Fare fare) {
        logger.info("Received request to add fare: {}", fare);
        Fare savedFare = fareService.addFare(fare);
        logger.info("Fare added successfully: {}", savedFare);
        return ResponseEntity.ok(savedFare);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getFare(@RequestParam String flightId, @RequestParam String seatClass) {
        logger.info("Fetching fare for flightId: {} and seatClass: {}", flightId, seatClass);
        Optional<Fare> fare = fareService.getFare(flightId, seatClass);

        if (fare.isPresent()) {
            logger.info("Fare found: {}", fare.get());
            return ResponseEntity.ok(fare.get());
        } else {
            logger.warn("Fare not found for flightId: {} and seatClass: {}", flightId, seatClass);
            return ResponseEntity.status(404).body("Fare not found for given flightId and seatClass");
        }
    }
}

