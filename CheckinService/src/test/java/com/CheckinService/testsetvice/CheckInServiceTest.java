package com.CheckinService.testsetvice;



import com.CheckinService.Dto.BookingResponse;
import com.CheckinService.Dto.PassengerDto;
import com.CheckinService.Enitities.CheckIn;
import com.CheckinService.Feignclient.BookingClient;
import com.CheckinService.Feignclient.SearchClient;
import com.CheckinService.Repository.CheckInRecordRepository;
import com.CheckinService.Service.CheckInService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CheckInServiceTest {

    @InjectMocks
    private CheckInService checkInService;

    @Mock
    private CheckInRecordRepository checkInRecordRepository;

    @Mock
    private BookingClient bookingClient;

    @Mock
    private SearchClient searchClient;

    private BookingResponse bookingResponse;
    private PassengerDto passenger;

    @BeforeEach
    void setUp() {
        passenger = new PassengerDto();
        passenger.setFirstName("John");
        passenger.setLastName("Doe");

        bookingResponse = new BookingResponse();
//        bookingResponse.setId(1L);
        bookingResponse.setStatus("BOOKED");
        bookingResponse.setFlightNumber("AI101");
        bookingResponse.setFlightDate("2025-04-10");
        bookingResponse.setPassengers(List.of(passenger));
    }

    @Test
    void testCheckIn_Successful() {
        when(bookingClient.getBookingById(1L)).thenReturn(bookingResponse);
        when(searchClient.getSeatStatus("AI101", "2025-04-10", "12A")).thenReturn("BOOKED");
        when(checkInRecordRepository.findByBookingIdAndSeatNumber(1L, "12A")).thenReturn(Optional.empty());

        CheckIn savedCheckIn = new CheckIn();
        savedCheckIn.setId(100L);
        when(checkInRecordRepository.save(any(CheckIn.class))).thenReturn(savedCheckIn);

        long checkInId = checkInService.checkIn(1L, 0, "12A");

        assertEquals(100L, checkInId);
        verify(checkInRecordRepository).save(any(CheckIn.class));
    }

    @Test
    void testCheckIn_InvalidBookingId() {
        when(bookingClient.getBookingById(1L)).thenReturn(null);

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> checkInService.checkIn(1L, 0, "12A"));

        assertTrue(ex.getMessage().contains("No booking found"));
    }

    @Test
    void testCheckIn_InvalidBookingStatus() {
        bookingResponse.setStatus("CANCELLED");
        when(bookingClient.getBookingById(1L)).thenReturn(bookingResponse);

        Exception ex = assertThrows(IllegalStateException.class,
                () -> checkInService.checkIn(1L, 0, "12A"));

        assertTrue(ex.getMessage().contains("Booking status is not BOOKED"));
    }

    @Test
    void testCheckIn_InvalidPassengerIndex() {
        when(bookingClient.getBookingById(1L)).thenReturn(bookingResponse);

        assertThrows(IllegalArgumentException.class,
                () -> checkInService.checkIn(1L, 5, "12A")); // out of bounds
    }

    @Test
    void testCheckIn_SeatAlreadyCheckedIn() {
        when(bookingClient.getBookingById(1L)).thenReturn(bookingResponse);
        when(checkInRecordRepository.findByBookingIdAndSeatNumber(1L, "12A"))
                .thenReturn(Optional.of(new CheckIn()));

        assertThrows(IllegalStateException.class,
                () -> checkInService.checkIn(1L, 0, "12A"));
    }

    @Test
    void testCheckIn_SeatNotBooked() {
        when(bookingClient.getBookingById(1L)).thenReturn(bookingResponse);
        when(checkInRecordRepository.findByBookingIdAndSeatNumber(1L, "12A"))
                .thenReturn(Optional.empty());
        when(searchClient.getSeatStatus("AI101", "2025-04-10", "12A")).thenReturn("AVAILABLE");

        assertThrows(IllegalStateException.class,
                () -> checkInService.checkIn(1L, 0, "12A"));
    }

    @Test
    void testGetCheckInRecord_Success() {
        CheckIn checkIn = new CheckIn();
        checkIn.setId(101L);
        when(checkInRecordRepository.findById(101L)).thenReturn(Optional.of(checkIn));

        CheckIn result = checkInService.getCheckInRecord(101L);
        assertEquals(101L, result.getId());
    }

    @Test
    void testGetCheckInRecord_NotFound() {
        when(checkInRecordRepository.findById(500L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(RuntimeException.class,
                () -> checkInService.getCheckInRecord(500L));
        assertTrue(ex.getMessage().contains("Check-in not found"));
    }
}
