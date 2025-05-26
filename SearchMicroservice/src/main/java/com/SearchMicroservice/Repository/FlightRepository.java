package com.SearchMicroservice.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.SearchMicroservice.Entities.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
 List<Flight> findByOriginIgnoreCase(String origin);
 List<Flight> findByDestinationIgnoreCase(String destination);
 List<Flight> findByFlightDate(LocalDate flightDate);
 List<Flight> findByFlightTime(LocalTime flightTime);

 Flight findByFlightIdAndFlightDate(String flightId, LocalDate flightDate);

}

