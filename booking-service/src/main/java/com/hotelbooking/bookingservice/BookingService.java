package com.hotelbooking.bookingservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RabbitMQSender rabbitMQSender;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    public List<Booking> getBookingsByUserId(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    public Booking createBooking(Booking booking) {
        booking.setStatus("CONFIRMED");
        Booking saved = bookingRepository.save(booking);
        rabbitMQSender.sendBookingNotification("Booking confirmed for user: " + booking.getUserId());
        return saved;
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}