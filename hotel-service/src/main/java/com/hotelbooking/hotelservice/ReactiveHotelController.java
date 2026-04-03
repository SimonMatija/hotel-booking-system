package com.hotelbooking.hotelservice;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/hotels/reactive")
public class ReactiveHotelController {

    @Autowired
    private ReactiveHotelService reactiveHotelService;

    @GetMapping
    public ResponseEntity<List<Hotel>> getAllHotelsReactive() {
        List<Hotel> hotels = reactiveHotelService.getAllHotelsReactive()
                .toList()
                .blockingGet();
        return ResponseEntity.ok(hotels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelByIdReactive(@PathVariable Long id) {
        try {
            Hotel hotel = reactiveHotelService.getHotelByIdReactive(id).blockingGet();
            return ResponseEntity.ok(hotel);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Hotel> createHotelReactive(@RequestBody Hotel hotel) {
        Hotel created = reactiveHotelService.createHotelReactive(hotel).blockingGet();
        return ResponseEntity.ok(created);
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<List<Hotel>> getHotelsByLocationReactive(@PathVariable String location) {
        List<Hotel> hotels = reactiveHotelService.getHotelsByLocationReactive(location)
                .toList()
                .blockingGet();
        return ResponseEntity.ok(hotels);
    }
}