package com.SearchMicroservice.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.SearchMicroservice.Entities.Flight;
import com.SearchMicroservice.Entities.Seat;
import com.SearchMicroservice.Service.FlightService;
import com.SearchMicroservice.Service.SeatServiceImpl;

import io.swagger.v3.oas.annotations.Operation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @Autowired
    private SeatServiceImpl seatService;
    
    
    @Operation(summary = "ADD flights")
    @PostMapping(value = "/add")
    public Flight addFlight(@RequestBody Flight flight) {
        return flightService.addFlight(flight);
    }
    
    @Operation(summary = "Get all flights")
    @GetMapping("/all")
    public List<Flight> getAllFlights() {
        return flightService.getAllFlights();
    }
    @Operation(summary = "Get all flightsBYId")
    @GetMapping("/{id}")
    public Flight getFlightById(@PathVariable Long id) {
        return flightService.getFlightById(id);
    }
    
    @Operation(summary = "Get availableSeats by flightId")
    @GetMapping("/{flightId}/available-seats")
    public List<Seat> getAvailableSeats(@PathVariable Long flightId) {
        return seatService.getAvailableSeats(flightId);
    }
    @Operation(summary = "Get  seats by seatId")
    @GetMapping("/seat/{seatId}")
    public Seat getSeatDetails(@PathVariable Long seatId) {
        return seatService.getSeatDetails(seatId);
    }
    
    @Operation(summary = "Get  seatStatus by Status")
    @GetMapping("/seats/status/{status}")
    public List<Seat> findSeatsByStatus(@PathVariable String status) {
        return seatService.findSeatsByStatus(status);
    }
    @Operation(summary = "Get seatClass by Class")
    @GetMapping("/seats/class/{seatClass}")
    public List<Seat> findSeatsByClass(@PathVariable String seatClass) {
        return seatService.findSeatsByClass(seatClass);
    }
    @Operation(summary = "Get origin by Orgin")
    @GetMapping("/origin/{origin}")
    public List<Flight> getFlightsByOrigin(@PathVariable String origin) {
        return flightService.getFlightsByOrigin(origin);
    }
    @Operation(summary = "Get destination by destination")
    @GetMapping("/destination/{destination}")
    public List<Flight> getFlightsByDestination(@PathVariable String destination) {
        return flightService.getFlightsByDestination(destination);
    }
    @Operation(summary = "Get by flightDate")
    @GetMapping("/flight-Date/{flightDate}")
    public List<Flight> getFlightsByFlightDate(@PathVariable String flightDate) {
        LocalDate date = LocalDate.parse(flightDate);
        return flightService.getFlightsByFlightDate(date);
    }
    
    @Operation(summary = "Get by FlightTime")
    @GetMapping("/flight-Time/{flightTime}")
    public ResponseEntity<List<Flight>> getFlightsByDepartureTime(@PathVariable String flightTime) {
        LocalTime time = LocalTime.parse(flightTime);
        List<Flight> flights = flightService.getFlightsByFlightTime(time);
        return ResponseEntity.ok(flights);
    }
    @Operation(summary = "Get seat status by flightId, flightDate and seatNumber")
    @GetMapping("/seats/status")
    public ResponseEntity<String> getSeatStatus(
            @RequestParam String flightId,
            @RequestParam String flightDate,
            @RequestParam String seatNumber) {
        
        // Fetch the flight using flightId and flightDate
        Flight flight = flightService.getFlightByFlightNumberAndDate(flightId, LocalDate.parse(flightDate));
        
        if (flight == null) {
            return ResponseEntity.badRequest().body("Flight not found");
        }

        // Fetch seat for that flight
        Seat seat = seatService.getSeatByFlightAndSeatNumber(flight, seatNumber);

        if (seat == null) {
            return ResponseEntity.badRequest().body("Seat not found");
        }

        return ResponseEntity.ok(seat.getSeatStatus());
    }

   
    
}
