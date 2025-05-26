package com.BookingMicroservice.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.BookingMicroservice.Dto.FareDTO;

@FeignClient(name = "FARE-SERVICE", url = "http://localhost:8084")
public interface FareServiceClient {
    @GetMapping("/fares/get")
    FareDTO getFare(@RequestParam("flightId") String flightId,
                    @RequestParam("flightDate") String flightDate);
}
