package com.BookingMicroservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BookingMicroservice.Enitities.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
  
}
