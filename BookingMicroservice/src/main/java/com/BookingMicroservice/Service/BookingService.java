package com.BookingMicroservice.Service;



import com.BookingMicroservice.Enitities.Booking;

public interface BookingService {

    long book(Booking booking);

    Booking getBooking(long id);

    void updateStatus(String status, long bookingId);
}
