package com.BookingMicroservice.Controller;


import com.BookingMicroservice.Enitities.Booking;
import com.BookingMicroservice.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // Create a new booking
    @PostMapping
    public ResponseEntity<Long> book(@RequestBody Booking booking) {
        long bookingId = bookingService.book(booking);
        return ResponseEntity.ok(bookingId);
    }

    // Get booking by ID
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBooking(@PathVariable long id) {
        Booking booking = bookingService.getBooking(id);
        return ResponseEntity.ok(booking);
    }

    // Update booking status
    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable("id") long bookingId,
                                               @RequestParam("status") String status) {
        bookingService.updateStatus(status, bookingId);
        return ResponseEntity.ok("Booking status updated successfully.");
    }
}
