package com.hotelbooking.bookingservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private RabbitMQSender rabbitMQSender;

    @InjectMocks
    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllBookings_ShouldReturnAllBookings() {
        Booking booking1 = new Booking(1L, 1L, LocalDate.now(), LocalDate.now().plusDays(3), "CONFIRMED");
        Booking booking2 = new Booking(2L, 2L, LocalDate.now(), LocalDate.now().plusDays(5), "CONFIRMED");
        when(bookingRepository.findAll()).thenReturn(Arrays.asList(booking1, booking2));

        List<Booking> result = bookingService.getAllBookings();

        assertEquals(2, result.size());
        verify(bookingRepository, times(1)).findAll();
    }

    @Test
    void getBookingById_ShouldReturnBooking_WhenExists() {
        Booking booking = new Booking(1L, 1L, LocalDate.now(), LocalDate.now().plusDays(3), "CONFIRMED");
        booking.setId(1L);
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        Optional<Booking> result = bookingService.getBookingById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getUserId());
    }

    @Test
    void getBookingById_ShouldReturnEmpty_WhenNotExists() {
        when(bookingRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Booking> result = bookingService.getBookingById(99L);

        assertFalse(result.isPresent());
    }

    @Test
    void createBooking_ShouldSaveAndSendNotification() {
        Booking booking = new Booking(1L, 1L, LocalDate.now(), LocalDate.now().plusDays(3), "PENDING");
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);
        doNothing().when(rabbitMQSender).sendBookingNotification(anyString());

        Booking result = bookingService.createBooking(booking);

        assertNotNull(result);
        assertEquals("CONFIRMED", result.getStatus());
        verify(rabbitMQSender, times(1)).sendBookingNotification(anyString());
    }

    @Test
    void deleteBooking_ShouldCallRepository() {
        doNothing().when(bookingRepository).deleteById(1L);

        bookingService.deleteBooking(1L);

        verify(bookingRepository, times(1)).deleteById(1L);
    }
}