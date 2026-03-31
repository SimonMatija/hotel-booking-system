package com.hotelbooking.hotelservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @GetMapping
    public List<Hotel> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable Long id) {
        return hotelService.getHotelById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/location/{location}")
    public List<Hotel> getHotelsByLocation(@PathVariable String location) {
        return hotelService.getHotelsByLocation(location);
    }

    @PostMapping
    public Hotel createHotel(@RequestBody Hotel hotel) {
        return hotelService.createHotel(hotel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hotel> updateHotel(@PathVariable Long id, @RequestBody Hotel hotel) {
        try {
            return ResponseEntity.ok(hotelService.updateHotel(id, hotel));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Hotel Service is running");
    }
}