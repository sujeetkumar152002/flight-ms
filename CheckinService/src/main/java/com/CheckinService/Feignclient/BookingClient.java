package com.CheckinService.Feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.CheckinService.Dto.BookingResponse;

@FeignClient(name = "BOOKING-SERVICE", url = "http://localhost:8083") 
public interface BookingClient {

    @GetMapping("/bookings/{id}")
    BookingResponse getBookingById(@PathVariable("id") Long bookingId);
}
