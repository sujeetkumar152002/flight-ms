package com.BookingMicroservice.Enitities;



import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String flightId;

    @Column(nullable = false)
    private LocalDate flightDate;

    @Column(nullable = false)
    private int available;

    public Inventory() {}

    public Inventory(String flightId, LocalDate flightDate, int available) {
        this.flightId = flightId;
        this.flightDate = flightDate;
        this.available = available;
    }

   
    public boolean isAvailable(int count) {
        return (available - count) > 5;
    }

   
    public int getBookableInventory() {
        return Math.max(available - 5, 0);
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

    public void setFlightId(String flightID) {
        this.flightId = flightId;
    }

    public LocalDate getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(LocalDate flightDate) {
        this.flightDate = flightDate;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "id=" + id +
                ", flightNumber='" + flightId + '\'' +
                ", flightDate=" + flightDate +
                ", available=" + available +
                '}';
    }
}
