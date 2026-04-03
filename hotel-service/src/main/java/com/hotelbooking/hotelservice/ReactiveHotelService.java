package com.hotelbooking.hotelservice;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReactiveHotelService {

    @Autowired
    private HotelRepository hotelRepository;

    public Observable<Hotel> getAllHotelsReactive() {
        return Observable.fromIterable(hotelRepository.findAll());
    }

    public Single<Hotel> getHotelByIdReactive(Long id) {
        return Single.fromCallable(() ->
                hotelRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Hotel not found"))
        );
    }

    public Single<Hotel> createHotelReactive(Hotel hotel) {
        return Single.fromCallable(() -> hotelRepository.save(hotel));
    }

    public Observable<Hotel> getHotelsByLocationReactive(String location) {
        return Observable.fromIterable(hotelRepository.findByLocation(location));
    }
}