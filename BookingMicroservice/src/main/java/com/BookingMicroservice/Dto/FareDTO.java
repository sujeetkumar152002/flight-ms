package com.BookingMicroservice.Dto;

public class FareDTO {
    private Long fareId;
    private String flightId;
    private String seatClass;
    private double price;
	public Long getFareId() {
		return fareId;
	}
	public void setFareId(Long fareId) {
		this.fareId = fareId;
	}
	public String getFlightId() {
		return flightId;
	}
	public void setFlightId(String flightId) {
		this.flightId = flightId;
	}
	public String getSeatClass() {
		return seatClass;
	}
	public void setSeatClass(String seatClass) {
		this.seatClass = seatClass;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

   
}
