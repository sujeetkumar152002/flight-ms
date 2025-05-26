package com.FareMicroservice.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Pattern.Flag;

@Entity
@Table(name = "fares")
public class Fare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fareId;

    @NotBlank(message = "Flight ID is required")
    private String flightId;

    @NotBlank
    @Pattern(regexp = "economy|business|first", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String seatClass;

    @Min(value = 0, message = "Fare must be >= 0")
    private double price;

    
    public Fare() {}

    public Fare(String flightId, String seatClass, double price) {
        this.flightId = flightId;
        this.seatClass = seatClass;
        this.price = price;
    }

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

	public Fare(Long fareId, @NotBlank(message = "Flight ID is required") String flightId,
			@NotBlank @Pattern(regexp = "economy|business|first", flags = Flag.CASE_INSENSITIVE) String seatClass,
			@Min(value = 0, message = "Fare must be >= 0") double price) {
		super();
		this.fareId = fareId;
		this.flightId = flightId;
		this.seatClass = seatClass;
		this.price = price;
	}



	@Override
	public String toString() {
		return "Fare [fareId=" + fareId + ", flightId=" + flightId + ", seatClass=" + seatClass + ", price=" + price
				+ "]";
	}

    
}
