package com.CheckinService.Dto;

import java.util.List;

public class BookingResponse {
    private Long bookingId;
    private String status;
    private String flightId;
    private String flightDate;
    private List<PassengerDto> passengers;
	public Long getBookingId() {
		return bookingId;
	}
	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFlightId() {
		return flightId;
	}
	public void setFlightNumber(String flightId) {
		this.flightId = flightId;
	}
	public String getFlightDate() {
		return flightDate;
	}
	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}
	public List<PassengerDto> getPassengers() {
		return passengers;
	}
	public void setPassengers(List<PassengerDto> passengers) {
		this.passengers = passengers;
	}

    
}
