package com.example.CinemaRoomRESTService;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class MovieTheaterController {
    public MovieTheater movieTheater = new MovieTheater();

    @GetMapping(value = "/seats")
    public Map seats() {
        return movieTheater.seats();
    }


    @PostMapping("/purchase")
    public ResponseEntity purchase(@RequestBody Seat seat) {
        if (seat.getRow() > movieTheater.totalRows || seat.getRow() < 0 || seat.getColumn() > movieTheater.totalColumns || seat.getColumn() < 0) {
            return new ResponseEntity<ErrorInfo>(new ErrorInfo("The number of a row or a column is out of bounds!"), HttpStatus.BAD_REQUEST);
        }
        Seat current = null;
        for (Map.Entry<String, Seat> entry : movieTheater.availableSeats.entrySet()) {
            if (entry.getValue().getRow() == seat.getRow() && entry.getValue().getColumn() == seat.getColumn()) {
                current = entry.getValue();
                movieTheater.availableSeats.remove(entry.getKey(), entry.getValue());
                movieTheater.purchasedSeats.put(entry.getKey(), entry.getValue());
            }
        }
        if (current == null) {
            return new ResponseEntity<ErrorInfo>(new ErrorInfo("The ticket has been already purchased!"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Map>(Map.of("token", current.getToken(), "ticket", current), HttpStatus.OK);
    }

    @PostMapping("/return")
    public ResponseEntity returnTicket(@RequestBody Map token) {
        String ticketToken = token.get("token").toString();
        if (movieTheater.purchasedSeats.containsKey(ticketToken)) {
            Seat returnedSeat = movieTheater.purchasedSeats.get(ticketToken);
            movieTheater.purchasedSeats.remove(returnedSeat.getToken(), returnedSeat);
            movieTheater.availableSeats.put(returnedSeat.getToken(), returnedSeat);
            return new ResponseEntity<Map>(Map.of("returned_ticket", returnedSeat), HttpStatus.OK);
        }
        return new ResponseEntity<Map>(Map.of("error", "Wrong token!"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/stats")
    public ResponseEntity stats(@RequestParam(value = "password", required = false) String password) {
        long currentIncome = 0;
        int numberOfAvailableSeats = movieTheater.availableSeats.size();
        int numberOfPurchasedTicket = movieTheater.purchasedSeats.size();
        for (Map.Entry<String, Seat> entry : movieTheater.purchasedSeats.entrySet()) {
            currentIncome += entry.getValue().getPrice();
        }
        if (password == null) {
            return new ResponseEntity<ErrorInfo>(new ErrorInfo("The password is wrong!"), HttpStatus.UNAUTHORIZED);
        } else if (password.equals("super_secret")) {
            return new ResponseEntity<Map>(Map.of("current_income", currentIncome, "number_of_available_seats", numberOfAvailableSeats, "number_of_purchased_tickets", numberOfPurchasedTicket), HttpStatus.OK);
        } else {
            return new ResponseEntity<ErrorInfo>(new ErrorInfo("The password is wrong!"), HttpStatus.UNAUTHORIZED);
        }

    }
}

