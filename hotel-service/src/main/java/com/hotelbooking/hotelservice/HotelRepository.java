package com.hotelbooking.hotelservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByLocation(String location);
    List<Hotel> findByStars(Integer stars);
}