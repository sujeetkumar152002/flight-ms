package com.CheckinService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CheckinService.Enitities.CheckIn;

import java.util.List;
import java.util.Optional;

@Repository
public interface CheckInRecordRepository extends JpaRepository<CheckIn, Long> {

    List<CheckIn> findByFlightNumberAndFlightDate(String flightNumber, java.time.LocalDate flightDate);

    List<CheckIn> findByBookingId(Long bookingId);

	Optional<CheckIn> findByBookingIdAndSeatNumber(Long bookingId, String seatNumber);
}
