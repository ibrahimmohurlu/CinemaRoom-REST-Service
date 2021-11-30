package com.example.CinemaRoomRESTService;


import com.fasterxml.jackson.annotation.JsonIgnore;


import java.util.UUID;

public class Seat {
    private int row;
    private int column;
    private int price;
    private boolean isBooked;
    private UUID token;

    public Seat() {

    }

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        this.isBooked = false;
        if (this.row <= 4) {
            this.price = 10;
        } else {
            this.price = 8;
        }
        this.token = UUID.randomUUID();
    }

    public int getPrice() {
        return price;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    @JsonIgnore
    public String getToken() {
        return token.toString();
    }

    public void setBooked(boolean booked) {
        this.isBooked = booked;
    }

    @JsonIgnore
    public boolean isBooked() {
        return isBooked;
    }

    @Override
    public boolean equals(Object o) {
        Seat seat = (Seat) o;
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        } else {
            return seat.getRow() == this.row && seat.getColumn() == this.column;
        }
    }

    @Override
    public String toString() {
        return "Seat{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }
}
