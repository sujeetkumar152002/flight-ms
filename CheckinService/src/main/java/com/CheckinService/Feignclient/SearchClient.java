package com.CheckinService.Feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "SEARCH-SERVICE", url = "http://localhost:8082")
public interface SearchClient {

    @GetMapping("/flights/seats/status")
    String getSeatStatus(@RequestParam String flightNumber,
                         @RequestParam String flightDate,
                         @RequestParam String seatNumber);
}
