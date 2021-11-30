package com.example.CinemaRoomRESTService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MovieTheater {
    public int totalRows = 9;
    public int totalColumns = 9;
    public ConcurrentMap<String, Seat> availableSeats = new ConcurrentHashMap<>();
    public ConcurrentMap<String, Seat> purchasedSeats = new ConcurrentHashMap<>();

    public MovieTheater() {
        for (int i = 0; i < totalRows; i++) {
            for (int j = 0; j < totalColumns; j++) {
                Seat temp = new Seat(i + 1, j + 1);
                availableSeats.put(temp.getToken(), temp);
            }
        }
    }

    public Map seats() {
        ConcurrentMap<String, Object> response = new ConcurrentHashMap<>();
        response.put("total_rows", totalRows);
        response.put("total_columns", totalColumns);
        List<Map> arr = new ArrayList<>();
        availableSeats.forEach((key, seat) -> {
            arr.add(Map.of("row", seat.getRow(), "column", seat.getColumn(), "price", seat.getPrice()));
        });
        response.put("available_seats", arr);
        return response;
    }

}