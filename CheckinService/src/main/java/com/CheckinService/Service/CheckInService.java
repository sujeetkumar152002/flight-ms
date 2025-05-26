package com.CheckinService.Service;

import com.CheckinService.Dto.BookingResponse;
import com.CheckinService.Dto.PassengerDto;
import com.CheckinService.Enitities.CheckIn;
import com.CheckinService.Feignclient.BookingClient;
import com.CheckinService.Feignclient.SearchClient;
import com.CheckinService.Repository.CheckInRecordRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CheckInService {

    private static final Logger logger = LoggerFactory.getLogger(CheckInService.class);

    private final CheckInRecordRepository checkInRecordRepository;
    private final BookingClient bookingClient;
    private final SearchClient searchClient;

    public CheckInService(CheckInRecordRepository checkInRecordRepository,
                          BookingClient bookingClient,
                          SearchClient searchClient) {
        this.checkInRecordRepository = checkInRecordRepository;
        this.bookingClient = bookingClient;
        this.searchClient = searchClient;
    }

    public long checkIn(Long bookingId, int passengerIndex, String seatNumber) {
        BookingResponse booking = bookingClient.getBookingById(bookingId);

        if (booking == null) {
            throw new IllegalArgumentException("No booking found with ID: " + bookingId);
        }

        if (!"BOOKED".equalsIgnoreCase(booking.getStatus())) {
            throw new IllegalStateException("Cannot check-in. Booking status is not BOOKED.");
        }

        if (passengerIndex < 0 || passengerIndex >= booking.getPassengers().size()) {
            throw new IllegalArgumentException("Invalid passenger index: " + passengerIndex);
        }

        
        Optional<CheckIn> existingCheckIn = checkInRecordRepository
                .findByBookingIdAndSeatNumber(bookingId, seatNumber);
        if (existingCheckIn.isPresent()) {
            throw new IllegalStateException("Passenger already checked in for this seat.");
        }

        String seatStatus = searchClient.getSeatStatus(
                booking.getFlightId(),
                booking.getFlightDate(),
                seatNumber
        );

        if (!"BOOKED".equalsIgnoreCase(seatStatus)) {
            throw new IllegalStateException("Cannot check-in. Seat is not booked.");
        }

        PassengerDto passenger = booking.getPassengers().get(passengerIndex);

        CheckIn checkIn = new CheckIn();
        checkIn.setBookingId(bookingId);
        checkIn.setFlightNumber(booking.getFlightId());
        checkIn.setFlightDate(LocalDate.parse(booking.getFlightDate()));
        checkIn.setSeatNumber(seatNumber);
        checkIn.setFirstName(passenger.getFirstName());
        checkIn.setLastName(passenger.getLastName());
        checkIn.setCheckInTime(LocalDateTime.now());

        CheckIn savedCheckIn = checkInRecordRepository.save(checkIn);

        logger.info("Passenger {} {} checked in successfully for flight {}, seat {}",
                passenger.getFirstName(),
                passenger.getLastName(),
                booking.getFlightId(),
                seatNumber
        );

        return savedCheckIn.getId();
    }

    public CheckIn getCheckInRecord(long id) {
        return checkInRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Check-in not found for id: " + id));
    }
}
