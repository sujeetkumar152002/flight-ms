package com.CheckinService.Controller;




import com.CheckinService.Enitities.CheckIn;
import com.CheckinService.Service.CheckInService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkin")
public class CheckInController {

    private final CheckInService checkInService;

    public CheckInController(CheckInService checkInService) {
        this.checkInService = checkInService;
    }

 
    @PostMapping
    public ResponseEntity<Long> checkInPassenger(
            @RequestParam Long bookingId,
            @RequestParam int passengerIndex,
            @RequestParam String seatNumber
    ) {
        long checkInId = checkInService.checkIn(bookingId, passengerIndex, seatNumber);
        return ResponseEntity.ok(checkInId);
    }

   
    @GetMapping("/{id}")
    public ResponseEntity<CheckIn> getCheckInRecord(@PathVariable long id) {
        CheckIn record = checkInService.getCheckInRecord(id);
        return ResponseEntity.ok(record);
    }
}
