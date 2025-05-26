package com.FareMicroservice.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.FareMicroservice.Entities.Fare;
import com.FareMicroservice.Repository.FareRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FareServiceImpl {

    @Autowired
    private FareRepository fareRepository;


    public Fare addFare(Fare fare) {
        switch (fare.getSeatClass().toLowerCase()) {
            case "economy":
                fare.setPrice(5000.00);
                break;
            case "business":
                fare.setPrice(20000.00);
                break;
            case "first":
                fare.setPrice(10000.00);
                break;
            default:
                throw new IllegalArgumentException("Invalid seat class: " + fare.getSeatClass());
        }

        return fareRepository.save(fare);
    }

   
    public List<Fare> getAllFares() {
        return fareRepository.findAll();
    }

   
    public Optional<Fare> getFare(String flightId, String seatClass) {
        return fareRepository.findByFlightIdAndSeatClass(flightId, seatClass);
    }
}
