package com.CheckinService.Enitities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class CheckIn {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Seat number is required")
    private String seatNumber;

    @NotNull(message = "Check-in time is required")
    private LocalDateTime checkInTime;

    @NotBlank(message = "Flight number is required")
    private String flightNumber;

    @NotNull(message = "Flight date is required")
    private LocalDate flightDate;

    @NotNull(message = "Booking ID is required")
    private Long bookingId;

    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(LocalDateTime checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public LocalDate getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(LocalDate flightDate) {
        this.flightDate = flightDate;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

	public CheckIn(Long id, @NotBlank(message = "Last name is required") String lastName,
			@NotBlank(message = "First name is required") String firstName,
			@NotBlank(message = "Seat number is required") String seatNumber,
			@NotNull(message = "Check-in time is required") LocalDateTime checkInTime,
			@NotBlank(message = "Flight number is required") String flightNumber,
			@NotNull(message = "Flight date is required") LocalDate flightDate,
			@NotNull(message = "Booking ID is required") Long bookingId) {
		super();
		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
		this.seatNumber = seatNumber;
		this.checkInTime = checkInTime;
		this.flightNumber = flightNumber;
		this.flightDate = flightDate;
		this.bookingId = bookingId;
	}

	public CheckIn() {
		super();
	}

	@Override
	public String toString() {
		return "CheckIn [id=" + id + ", lastName=" + lastName + ", firstName=" + firstName + ", seatNumber="
				+ seatNumber + ", checkInTime=" + checkInTime + ", flightNumber=" + flightNumber + ", flightDate="
				+ flightDate + ", bookingId=" + bookingId + "]";
	}
    
    
}
