package com.SearchMicroservice.Service;

import com.SearchMicroservice.Entities.Flight;
import com.SearchMicroservice.Entities.Seat;
import com.SearchMicroservice.Repository.SeatRepository;
import com.SearchMicroservice.Exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(SeatServiceImpl.class);

    @Autowired
	public SeatRepository seatRepository;

    public List<Seat> getAvailableSeats(Long Id) {
        logger.info("Fetching available seats for flight ID: {}", Id);
        return seatRepository.findByFlightIdAndSeatStatusIgnoreCase(Id, "available");
    }

    public void bookSeat(Long seatId) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new SeatNotFoundException("Seat not found with ID: " + seatId));
        
        if ("available".equalsIgnoreCase(seat.getSeatStatus())) {
            seat.setSeatStatus("booked");
            seatRepository.save(seat);
            logger.info("Seat {} booked successfully", seatId);
        } else {
            throw new IllegalStateException("Seat is not available for booking");
        }
    }

    public List<Seat> findSeatsByStatus(String status) {
        logger.info("Fetching seats with status: {}", status);
        return seatRepository.findBySeatStatus(status);
    }

    public List<Seat> findSeatsByClass(String seatClass) {
        logger.info("Fetching seats of class: {}", seatClass);
        return seatRepository.findBySeatClass(seatClass);
    }

    public Seat getSeatDetails(Long seatId) {
        logger.info("Fetching details for seat ID: {}", seatId);
        return seatRepository.findById(seatId)
                .orElseThrow(() -> new SeatNotFoundException("Seat not found with ID: " + seatId));
    }

    public void updateSeatAvailability(String flightId, String seatClass, int numberOfSeats) {
        List<Seat> availableSeats = seatRepository
                .findByFlight_FlightIdAndSeatClassIgnoreCaseAndSeatStatusIgnoreCase(flightId, seatClass, "available");

        if (availableSeats.size() < numberOfSeats) {
            throw new InsufficientSeatsException("Not enough available seats for flight " + flightId);
        }

        for (int i = 0; i < numberOfSeats; i++) {
            Seat seat = availableSeats.get(i);
            seat.setSeatStatus("booked");
            seatRepository.save(seat);
            logger.info("Seat {} booked successfully", seat.getSeatNumber());
        }
    }

    public Seat getSeatByFlightAndSeatNumber(Flight flight, String seatNumber) {
        logger.info("Fetching seat {} for flight {}", seatNumber, flight.getFlightId());
        Seat seat = seatRepository.findByFlightAndSeatNumberIgnoreCase(flight, seatNumber);

        if (seat == null) {
            throw new SeatNotFoundException("Seat " + seatNumber + " not found for flight " + flight.getFlightId());
        }

        return seat;
    }
}
