package com.FareMicroservice.Repository;

import com.FareMicroservice.Entities.Fare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;


@Repository
public interface FareRepository extends JpaRepository<Fare, Long> {
    Optional<Fare> findByFlightIdAndSeatClass(String flightId, String seatClass);
}
