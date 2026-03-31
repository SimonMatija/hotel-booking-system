package com.hotelbooking.hotelservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    public Optional<Hotel> getHotelById(Long id) {
        return hotelRepository.findById(id);
    }

    public List<Hotel> getHotelsByLocation(String location) {
        return hotelRepository.findByLocation(location);
    }

    public Hotel createHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public Hotel updateHotel(Long id, Hotel updatedHotel) {
        return hotelRepository.findById(id).map(hotel -> {
            hotel.setName(updatedHotel.getName());
            hotel.setLocation(updatedHotel.getLocation());
            hotel.setDescription(updatedHotel.getDescription());
            hotel.setStars(updatedHotel.getStars());
            return hotelRepository.save(hotel);
        }).orElseThrow(() -> new RuntimeException("Hotel not found"));
    }

    public void deleteHotel(Long id) {
        hotelRepository.deleteById(id);
    }
}