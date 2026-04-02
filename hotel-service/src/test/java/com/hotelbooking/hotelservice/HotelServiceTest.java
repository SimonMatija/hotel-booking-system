package com.hotelbooking.hotelservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private HotelService hotelService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllHotels_ShouldReturnAllHotels() {
        Hotel hotel1 = new Hotel("Hotel A", "Paris", "Nice hotel", 5);
        Hotel hotel2 = new Hotel("Hotel B", "London", "Good hotel", 4);
        when(hotelRepository.findAll()).thenReturn(Arrays.asList(hotel1, hotel2));

        List<Hotel> result = hotelService.getAllHotels();

        assertEquals(2, result.size());
        verify(hotelRepository, times(1)).findAll();
    }

    @Test
    void getHotelById_ShouldReturnHotel_WhenExists() {
        Hotel hotel = new Hotel("Hotel A", "Paris", "Nice hotel", 5);
        hotel.setId(1L);
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));

        Optional<Hotel> result = hotelService.getHotelById(1L);

        assertTrue(result.isPresent());
        assertEquals("Hotel A", result.get().getName());
    }

    @Test
    void getHotelById_ShouldReturnEmpty_WhenNotExists() {
        when(hotelRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Hotel> result = hotelService.getHotelById(99L);

        assertFalse(result.isPresent());
    }

    @Test
    void createHotel_ShouldSaveAndReturnHotel() {
        Hotel hotel = new Hotel("Hotel A", "Paris", "Nice hotel", 5);
        when(hotelRepository.save(hotel)).thenReturn(hotel);

        Hotel result = hotelService.createHotel(hotel);

        assertNotNull(result);
        assertEquals("Hotel A", result.getName());
        verify(hotelRepository, times(1)).save(hotel);
    }

    @Test
    void deleteHotel_ShouldCallRepository() {
        doNothing().when(hotelRepository).deleteById(1L);

        hotelService.deleteHotel(1L);

        verify(hotelRepository, times(1)).deleteById(1L);
    }
}