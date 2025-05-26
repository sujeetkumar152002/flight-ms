package com.BookingMicroservice.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.BookingMicroservice.Enitities.Inventory;

@FeignClient(name = "SEARCH-SERVICE", url = "http://localhost:8082")
public interface SearchServiceClient {
    @GetMapping("/inventory/find")
    Inventory getInventory(@RequestParam("flightId") String flightId,
                           @RequestParam("flightDate") String flightDate);

    @PutMapping("/inventory/update")
    void updateInventory(@RequestParam("flightId") String flightId,
                         @RequestParam("flightDate") String flightDate,
                         @RequestParam("seatsToBook") int seatsToBook);
}
