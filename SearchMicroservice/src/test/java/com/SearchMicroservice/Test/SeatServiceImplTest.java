package com.SearchMicroservice.Test;



import com.SearchMicroservice.Entities.Flight;
import com.SearchMicroservice.Entities.Seat;
import com.SearchMicroservice.Exception.InsufficientSeatsException;
import com.SearchMicroservice.Exception.SeatNotFoundException;
import com.SearchMicroservice.Repository.SeatRepository;
import com.SearchMicroservice.Service.SeatServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SeatServiceImplTest {

    private SeatServiceImpl seatService;
    private SeatRepository seatRepository;

    @BeforeEach
    void setUp() {
        seatRepository = mock(SeatRepository.class);
        seatService = new SeatServiceImpl();
        seatService.seatRepository = seatRepository; // direct field access since it's not constructor-injected
    }

    @Test
    void testGetAvailableSeats() {
        Seat seat1 = new Seat(); seat1.setSeatStatus("available");
        Seat seat2 = new Seat(); seat2.setSeatStatus("available");

        when(seatRepository.findByFlightIdAndSeatStatusIgnoreCase(1L, "available"))
                .thenReturn(Arrays.asList(seat1, seat2));

        List<Seat> seats = seatService.getAvailableSeats(1L);
        assertEquals(2, seats.size());
    }

    @Test
    void testBookSeat_Success() {
        Seat seat = new Seat();
        seat.setSeatId(10L);
        seat.setSeatStatus("available");

        when(seatRepository.findById(10L)).thenReturn(Optional.of(seat));

        seatService.bookSeat(10L);

        assertEquals("booked", seat.getSeatStatus());
        verify(seatRepository, times(1)).save(seat);
    }

    @Test
    void testBookSeat_AlreadyBooked() {
        Seat seat = new Seat();
        seat.setSeatId(20L);
        seat.setSeatStatus("booked");

        when(seatRepository.findById(20L)).thenReturn(Optional.of(seat));

        assertThrows(IllegalStateException.class, () -> seatService.bookSeat(20L));
    }

    @Test
    void testBookSeat_NotFound() {
        when(seatRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(SeatNotFoundException.class, () -> seatService.bookSeat(99L));
    }

    @Test
    void testFindSeatsByStatus() {
        when(seatRepository.findBySeatStatus("booked")).thenReturn(Arrays.asList(new Seat(), new Seat()));
        List<Seat> result = seatService.findSeatsByStatus("booked");
        assertEquals(2, result.size());
    }

    @Test
    void testFindSeatsByClass() {
        when(seatRepository.findBySeatClass("economy")).thenReturn(Arrays.asList(new Seat()));
        List<Seat> result = seatService.findSeatsByClass("economy");
        assertEquals(1, result.size());
    }

    @Test
    void testGetSeatDetails_Success() {
        Seat seat = new Seat(); seat.setSeatId(1L);
        when(seatRepository.findById(1L)).thenReturn(Optional.of(seat));

        Seat found = seatService.getSeatDetails(1L);
        assertEquals(1L, found.getSeatId());
    }

    @Test
    void testGetSeatDetails_NotFound() {
        when(seatRepository.findById(123L)).thenReturn(Optional.empty());
        assertThrows(SeatNotFoundException.class, () -> seatService.getSeatDetails(123L));
    }

    @Test
    void testUpdateSeatAvailability_Success() {
        Seat seat1 = new Seat(); seat1.setSeatStatus("available");
        Seat seat2 = new Seat(); seat2.setSeatStatus("available");

        when(seatRepository
                .findByFlight_FlightIdAndSeatClassIgnoreCaseAndSeatStatusIgnoreCase("XX123", "business", "available"))
                .thenReturn(Arrays.asList(seat1, seat2));

        seatService.updateSeatAvailability("XX123", "business", 2);

        assertEquals("booked", seat1.getSeatStatus());
        assertEquals("booked", seat2.getSeatStatus());
        verify(seatRepository, times(2)).save(any());
    }

    @Test
    void testUpdateSeatAvailability_InsufficientSeats() {
        when(seatRepository
                .findByFlight_FlightIdAndSeatClassIgnoreCaseAndSeatStatusIgnoreCase("XX123", "economy", "available"))
                .thenReturn(List.of(new Seat()));

        assertThrows(InsufficientSeatsException.class, () ->
                seatService.updateSeatAvailability("XX123", "economy", 2));
    }

    @Test
    void testGetSeatByFlightAndSeatNumber_Success() {
        Flight flight = new Flight(); flight.setFlightId("AI101");
        Seat seat = new Seat(); seat.setSeatNumber("12A");

        when(seatRepository.findByFlightAndSeatNumberIgnoreCase(flight, "12A"))
                .thenReturn(seat);

        Seat found = seatService.getSeatByFlightAndSeatNumber(flight, "12A");
        assertEquals("12A", found.getSeatNumber());
    }

    @Test
    void testGetSeatByFlightAndSeatNumber_NotFound() {
        Flight flight = new Flight(); flight.setFlightId("AI999");
        when(seatRepository.findByFlightAndSeatNumberIgnoreCase(flight, "1B")).thenReturn(null);

        assertThrows(SeatNotFoundException.class, () ->
                seatService.getSeatByFlightAndSeatNumber(flight, "1B"));
    }
}
