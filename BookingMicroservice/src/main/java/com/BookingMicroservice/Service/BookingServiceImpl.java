package com.BookingMicroservice.Service;
import com.BookingMicroservice.Exceptions.BookingNotFoundException;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BookingMicroservice.Dto.FareDTO;
import com.BookingMicroservice.Enitities.Booking;
import com.BookingMicroservice.Enitities.Inventory;
import com.BookingMicroservice.Enum.BookingStatus;
import com.BookingMicroservice.Exceptions.FareMismatchException;
import com.BookingMicroservice.Exceptions.InventoryNotAvailableException;
import com.BookingMicroservice.FeignClient.FareServiceClient;
import com.BookingMicroservice.FeignClient.SearchServiceClient;
import com.BookingMicroservice.Repository.BookingRepository;

@Service
public class BookingServiceImpl implements BookingService {

    private static final Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private FareServiceClient fareServiceClient;

    @Autowired
    private SearchServiceClient searchServiceClient;

    @Override
    public long book(Booking booking) {
        logger.info("Validating fare from Fare Microservice...");
        validateFare(booking);

        logger.info("Checking seat availability from Search Microservice...");
        Inventory inventory = searchServiceClient.getInventory(
                booking.getFlightId(), booking.getFlightDate().toString());

        if (inventory == null || !inventory.isAvailable(booking.getPassengers().size())) {
            throw new InventoryNotAvailableException("Not enough seats available for booking.");
        }

        logger.info("Updating inventory via Search Microservice...");
        searchServiceClient.updateInventory(
                booking.getFlightId(), booking.getFlightDate().toString(), booking.getPassengers().size());

        logger.info("Saving booking ...");
        booking.setStatus(BookingStatus.BOOKED);
        booking.setBookingDate(new Date());
        booking.getPassengers().forEach(p -> p.setBooking(booking));

        long id = bookingRepository.save(booking).getId();
        logger.info("Booking confirmed. Booking ID: {}", id);
        return id;
    }

    @Override
    public Booking getBooking(long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with ID: " + id));
    }

    @Override
    public void updateStatus(String status, long bookingId) {
        Booking booking = getBooking(bookingId);
        booking.setStatus(BookingStatus.valueOf(status));
        bookingRepository.save(booking);
        logger.info("Booking status updated to: {}", status);
    }

    private void validateFare(Booking booking) {
        FareDTO fareDTO = fareServiceClient.getFare(
                booking.getFlightId(), booking.getFlightDate().toString());

        if (fareDTO == null || !booking.getFare().equals(fareDTO.getPrice())) {
            throw new FareMismatchException("Fare mismatch! Expected: " + booking.getFare() +
                    ", Found: " + (fareDTO != null ? fareDTO.getPrice() : "null"));
        }

        logger.info("Fare validated: {}", fareDTO.getPrice());
    }
}
