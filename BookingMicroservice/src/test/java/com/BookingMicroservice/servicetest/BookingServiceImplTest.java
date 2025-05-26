package com.BookingMicroservice.servicetest;

import com.BookingMicroservice.Dto.FareDTO;
import com.BookingMicroservice.Enitities.Booking;
import com.BookingMicroservice.Enitities.Inventory;
import com.BookingMicroservice.Enitities.Passenger;
import com.BookingMicroservice.Enum.BookingStatus;
import com.BookingMicroservice.Exceptions.FareMismatchException;
import com.BookingMicroservice.Exceptions.InventoryNotAvailableException;
import com.BookingMicroservice.FeignClient.FareServiceClient;
import com.BookingMicroservice.FeignClient.SearchServiceClient;
import com.BookingMicroservice.Repository.BookingRepository;
import com.BookingMicroservice.Service.BookingServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class BookingServiceImplTest {

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private FareServiceClient fareServiceClient;

    @Mock
    private SearchServiceClient searchServiceClient;

    private Booking booking;
    private FareDTO fareDTO;
    private Inventory inventory;

    @BeforeEach
    void init() {
        booking = new Booking();
        booking.setFlightId("AI101");
//        booking.setFlightDate(new Date());
//        booking.setFare(5000.0);
        Passenger passenger1 = new Passenger();
        Passenger passenger2 = new Passenger();
        booking.setPassengers(List.of(passenger1, passenger2));

        fareDTO = new FareDTO();
        fareDTO.setFlightId("AI101");
        fareDTO.setPrice(5000.0);

        inventory = new Inventory();
//        inventory.setCount(10);
    }

    @Test
    void testBook_SuccessfulBooking() {
        when(fareServiceClient.getFare(anyString(), anyString())).thenReturn(fareDTO);
        when(searchServiceClient.getInventory(anyString(), anyString())).thenReturn(inventory);
        when(bookingRepository.save(any(Booking.class))).thenAnswer(i -> {
            Booking saved = i.getArgument(0);
            saved.setId(100L);
            return saved;
        });

        long bookingId = bookingService.book(booking);

        assertEquals(100L, bookingId);
        assertEquals(BookingStatus.BOOKED, booking.getStatus());
        assertNotNull(booking.getBookingDate());
        verify(searchServiceClient).updateInventory(eq("AI101"), anyString(), eq(2));
    }

    @Test
    void testBook_PassengerBookingReferenceSet() {
        when(fareServiceClient.getFare(anyString(), anyString())).thenReturn(fareDTO);
        when(searchServiceClient.getInventory(anyString(), anyString())).thenReturn(inventory);
        when(bookingRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        bookingService.book(booking);

        for (Passenger p : booking.getPassengers()) {
            assertEquals(booking, p.getBooking());
        }
    }

//    @Test
//    void testBook_FareMismatch_ThrowsException() {
//        fareDTO.setPrice(6000.0); // mismatch
//        when(fareServiceClient.getFare(anyString(), anyString())).thenReturn(fareDTO);
//
//        assertThrows(FareMismatchException.class, () -> bookingService.book(booking));
//    }
//
//    @Test
//    void testBook_InventoryNotAvailable_ThrowsException() {
//        when(fareServiceClient.getFare(anyString(), anyString())).thenReturn(fareDTO);
//        inventory.setCount(1); // not enough for 2 passengers
//        when(searchServiceClient.getInventory(anyString(), anyString())).thenReturn(inventory);
//
//        assertThrows(InventoryNotAvailableException.class, () -> bookingService.book(booking));
//    }
//
//    @Test
//    void testUpdateStatus_ShouldUpdateSuccessfully() {
//        Booking savedBooking = new Booking();
//        savedBooking.setId(100);
//        savedBooking.setStatus(BookingStatus.BOOKED);
//        when(bookingRepository.findById(100L)).thenReturn(Optional.of(savedBooking));
//        when(bookingRepository.save(any())).thenReturn(savedBooking);
//
//        bookingService.updateStatus("CANCELLED", 100L);
//        assertEquals(BookingStatus.CANCELLED, savedBooking.getStatus());
//    }
//
//    @Test
//    void testGetBooking_ShouldReturnBooking() {
//        Booking savedBooking = new Booking();
//        savedBooking.setId(10);
//        when(bookingRepository.findById(10L)).thenReturn(Optional.of(savedBooking));
//
//        Booking result = bookingService.getBooking(10L);
//        assertEquals(10L, result.getId());
//    }

    @Test
    void testGetBooking_NotFound_ShouldThrowException() {
        when(bookingRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> bookingService.getBooking(99L));
        assertTrue(exception.getMessage().contains("Booking not found"));
    }
}
