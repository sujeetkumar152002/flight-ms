package com.SearchMicroservice.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Pattern.Flag;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "flights")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotBlank(message = "Flight Id cannot be blank")
    @Size(max = 100, message = "Flight Id must be at most 100 characters")
    @Column(nullable = false, length = 100)
    private String flightId;

    @NotNull(message = "flightdate cannot be null")
    @FutureOrPresent(message = "flight date must be today or in the future")
    @Column(nullable = false)
    private LocalDate flightDate;

    

    @NotBlank(message = "Origin cannot be blank")
    @Size(max = 50, message = "Origin must be at most 50 characters")
    @Column(nullable = false)
    private String origin;

    @NotBlank(message = "Destination cannot be blank")
    @Size(max = 50, message = "Destination must be at most 50 characters")
    @Column(nullable = false)
    private String destination;

    @Min(value = 1, message = "Total number of seats must be at least 1")
    @Column(nullable = false)
    private int totalNoOfSeats = 100;  
    
    @Column(nullable = false)
    private int availableSeats;

    @NotBlank(message = "Status cannot be blank")
    @Pattern(regexp = "scheduled|delayed|cancelled|completed", flags = Pattern.Flag.CASE_INSENSITIVE, 
             message = "Status must be one of: scheduled, delayed, cancelled, completed")
    @Column(nullable = false, length = 20)
    private String status;
    
    
    @NotNull
    private LocalTime flightTime;
    
    

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Seat> seats = new ArrayList<>();



	public Flight() {
		super();
	}



	public Flight(Long id,
			@NotBlank(message = "Flight Id cannot be blank") @Size(max = 100, message = "Flight Id must be at most 100 characters") String flightId,
			@NotNull(message = "flightdate cannot be null") @FutureOrPresent(message = "flight date must be today or in the future") LocalDate flightDate,
			@NotBlank(message = "Origin cannot be blank") @Size(max = 50, message = "Origin must be at most 50 characters") String origin,
			@NotBlank(message = "Destination cannot be blank") @Size(max = 50, message = "Destination must be at most 50 characters") String destination,
			@Min(value = 1, message = "Total number of seats must be at least 1") int totalNoOfSeats,
			int availableSeats,
			@NotBlank(message = "Status cannot be blank") @Pattern(regexp = "scheduled|delayed|cancelled|completed", flags = Flag.CASE_INSENSITIVE, message = "Status must be one of: scheduled, delayed, cancelled, completed") String status,
			@NotNull LocalTime flightTime, List<Seat> seats) {
		super();
		Id = id;
		this.flightId = flightId;
		this.flightDate = flightDate;
		this.origin = origin;
		this.destination = destination;
		this.totalNoOfSeats = totalNoOfSeats;
		this.availableSeats = availableSeats;
		this.status = status;
		this.flightTime = flightTime;
		this.seats = seats;
	}



	public Long getId() {
		return Id;
	}



	public void setId(Long id) {
		Id = id;
	}



	public String getFlightId() {
		return flightId;
	}



	public void setFlightId(String flightId) {
		this.flightId = flightId;
	}



	public LocalDate getFlightDate() {
		return flightDate;
	}



	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
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



	public int getTotalNoOfSeats() {
		return totalNoOfSeats;
	}



	public void setTotalNoOfSeats(int totalNoOfSeats) {
		this.totalNoOfSeats = totalNoOfSeats;
	}



	public int getAvailableSeats() {
		return availableSeats;
	}



	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public LocalTime getFlightTime() {
		return flightTime;
	}



	public void setFlightTime(LocalTime flightTime) {
		this.flightTime = flightTime;
	}



	public List<Seat> getSeats() {
		return seats;
	}



	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}



	@Override
	public String toString() {
		return "Flight [Id=" + Id + ", flightId=" + flightId + ", flightDate=" + flightDate + ", origin=" + origin
				+ ", destination=" + destination + ", totalNoOfSeats=" + totalNoOfSeats + ", availableSeats="
				+ availableSeats + ", status=" + status + ", flightTime=" + flightTime + ", seats=" + seats + "]";
	}



    

 }