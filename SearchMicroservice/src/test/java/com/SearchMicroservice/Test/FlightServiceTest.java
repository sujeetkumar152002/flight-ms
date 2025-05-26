package com.SearchMicroservice.Test;

import com.SearchMicroservice.Entities.Flight;
import com.SearchMicroservice.Exception.FlightNotFoundException;
import com.SearchMicroservice.Repository.FlightRepository;
import com.SearchMicroservice.Service.FlightService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FlightServiceTest {

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private FlightService flightService;

    private Flight sampleFlight;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleFlight = new Flight();
        sampleFlight.setId(1L);
        sampleFlight.setFlightId("AI101");
        sampleFlight.setOrigin("Delhi");
        sampleFlight.setDestination("Mumbai");
        sampleFlight.setFlightDate(LocalDate.of(2025, 4, 15));
        sampleFlight.setFlightTime(LocalTime.of(10, 0));
    }

    @Test
    void testAddFlight() {
        when(flightRepository.save(any(Flight.class))).thenReturn(sampleFlight);

        Flight result = flightService.addFlight(sampleFlight);

        assertNotNull(result);
        assertEquals("AI101", result.getFlightId());
        verify(flightRepository, times(1)).save(sampleFlight);
    }

    @Test
    void testGetAllFlights() {
        when(flightRepository.findAll()).thenReturn(List.of(sampleFlight));

        List<Flight> flights = flightService.getAllFlights();

        assertEquals(1, flights.size());
        assertEquals("Delhi", flights.get(0).getOrigin());
    }

    @Test
    void testGetFlightById_Success() {
        when(flightRepository.findById(1L)).thenReturn(Optional.of(sampleFlight));

        Flight result = flightService.getFlightById(1L);

        assertEquals("Mumbai", result.getDestination());
    }

    @Test
    void testGetFlightById_NotFound() {
        when(flightRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(FlightNotFoundException.class, () -> flightService.getFlightById(99L));
    }

    @Test
    void testGetFlightsByOrigin() {
        when(flightRepository.findByOriginIgnoreCase("delhi")).thenReturn(List.of(sampleFlight));

        List<Flight> flights = flightService.getFlightsByOrigin("  Delhi ");

        assertFalse(flights.isEmpty());
        assertEquals("Mumbai", flights.get(0).getDestination());
    }

    @Test
    void testGetFlightsByDestination() {
        when(flightRepository.findByDestinationIgnoreCase("mumbai")).thenReturn(List.of(sampleFlight));

        List<Flight> flights = flightService.getFlightsByDestination("Mumbai");

        assertEquals(1, flights.size());
        assertEquals("Delhi", flights.get(0).getOrigin());
    }

    @Test
    void testGetFlightsByFlightDate() {
        when(flightRepository.findByFlightDate(sampleFlight.getFlightDate())).thenReturn(List.of(sampleFlight));

        List<Flight> flights = flightService.getFlightsByFlightDate(sampleFlight.getFlightDate());

        assertEquals(1, flights.size());
        assertEquals("AI101", flights.get(0).getFlightId());
    }

    @Test
    void testGetFlightsByFlightTime() {
        when(flightRepository.findByFlightTime(sampleFlight.getFlightTime())).thenReturn(List.of(sampleFlight));

        List<Flight> flights = flightService.getFlightsByFlightTime(sampleFlight.getFlightTime());

        assertEquals(1, flights.size());
        assertEquals("Delhi", flights.get(0).getOrigin());
    }

    @Test
    void testGetFlightByFlightNumberAndDate_Success() {
        when(flightRepository.findByFlightIdAndFlightDate("AI101", sampleFlight.getFlightDate()))
                .thenReturn(sampleFlight);

        Flight flight = flightService.getFlightByFlightNumberAndDate("AI101", sampleFlight.getFlightDate());

        assertEquals("Mumbai", flight.getDestination());
    }

    @Test
    void testGetFlightByFlightNumberAndDate_NotFound() {
        when(flightRepository.findByFlightIdAndFlightDate("XX123", sampleFlight.getFlightDate()))
                .thenReturn(null);

        assertThrows(FlightNotFoundException.class, () ->
                flightService.getFlightByFlightNumberAndDate("XX123", sampleFlight.getFlightDate()));
    }
}
