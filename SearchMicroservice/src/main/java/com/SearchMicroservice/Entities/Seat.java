package com.SearchMicroservice.Entities;



import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Pattern.Flag;

@Entity
@Table(name = "seats")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;  

    @Column(nullable = false)
    private String seatNumber; 

    @NotBlank(message = "Seat class cannot be empty")
    @Pattern(regexp = "first|business|economy", flags = Pattern.Flag.CASE_INSENSITIVE,
             message = "Seat class must be one of: first, business, economy")
    @Column(nullable = false)
    private String seatClass;

    @NotBlank(message = "Seat status cannot be empty")
    @Pattern(regexp = "booked|available|reserved", flags = Pattern.Flag.CASE_INSENSITIVE,
             message = "Seat status must be one of: booked, available, reserved")
    @Column(nullable = false)
    private String seatStatus;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    @JsonBackReference
    private Flight flight;

   
    public Seat() {}


	public Long getSeatId() {
		return seatId;
	}


	public void setSeatId(Long seatId) {
		this.seatId = seatId;
	}


	public String getSeatNumber() {
		return seatNumber;
	}


	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}


	public String getSeatClass() {
		return seatClass;
	}


	public void setSeatClass(String seatClass) {
		this.seatClass = seatClass;
	}


	public String getSeatStatus() {
		return seatStatus;
	}


	public void setSeatStatus(String seatStatus) {
		this.seatStatus = seatStatus;
	}


	public Flight getFlight() {
		return flight;
	}


	public void setFlight(Flight flight) {
		this.flight = flight;
	}


	public Seat(Long seatId, String seatNumber,
			@NotBlank(message = "Seat class cannot be empty") @Pattern(regexp = "first|business|economy", flags = Flag.CASE_INSENSITIVE, message = "Seat class must be one of: first, business, economy") String seatClass,
			@NotBlank(message = "Seat status cannot be empty") @Pattern(regexp = "booked|available|reserved", flags = Flag.CASE_INSENSITIVE, message = "Seat status must be one of: booked, available, reserved") String seatStatus,
			Flight flight) {
		super();
		this.seatId = seatId;
		this.seatNumber = seatNumber;
		this.seatClass = seatClass;
		this.seatStatus = seatStatus;
		this.flight = flight;
	}


	@Override
	public String toString() {
		return "Seat [seatId=" + seatId + ", seatNumber=" + seatNumber + ", seatClass=" + seatClass + ", seatStatus="
				+ seatStatus + ", flight=" + flight + "]";
	}

    
}