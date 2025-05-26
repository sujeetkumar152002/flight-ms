package com.BookingMicroservice.Enitities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.BookingMicroservice.Enum.BookingStatus;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Flight id is required")
    @Column(name = "flight_id")
    private String flightId;

    @NotBlank(message = "Origin is required")
    private String origin;

    @NotBlank(message = "Destination is required")
    private String destination;

    @NotNull(message = "Flight date is required")
    private LocalDate flightDate;

    @NotNull(message = "Booking date is required")
    @Temporal(TemporalType.TIMESTAMP)
    @PastOrPresent(message = "Booking date cannot be in the future")
    private Date bookingDate;

    @NotNull(message = "Fare is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Fare must be a positive number")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal fare;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Booking status is required")
    private BookingStatus status;

    @NotNull(message = "User ID is required")
    private Long userId;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Passenger> passengers;

   
    public Booking() {}

    public Booking(Long id, String flightId, String origin, String destination, LocalDate flightDate, Date bookingDate,
                   BigDecimal fare, BookingStatus status, Long userId, List<Passenger> passengers) {
        this.id = id;
        this.flightId = flightId;
        this.origin = origin;
        this.destination = destination;
        this.flightDate = flightDate;
        this.bookingDate = bookingDate;
        this.fare = fare;
        this.status = status;
        this.userId = userId;
        this.passengers = passengers;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFlightId() {
		return flightId;
	}

	public void setFlightId(String flightId) {
		this.flightId = flightId;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public LocalDate getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}

	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public BigDecimal getFare() {
		return fare;
	}

	public void setFare(BigDecimal fare) {
		this.fare = fare;
	}

	public BookingStatus getStatus() {
		return status;
	}

	public void setStatus(BookingStatus status) {
		this.status = status;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}
    
   
}
