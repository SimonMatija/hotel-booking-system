package com.hotelbooking.bookingservice;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookingServiceE2ETest {

    @LocalServerPort
    private int port;

    @MockBean
    private RabbitMQSender rabbitMQSender;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void shouldGetAllBookings() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/bookings")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    @Test
    void shouldCreateBooking() {
        String bookingJson = """
                {
                    "userId": 1,
                    "hotelId": 1,
                    "checkIn": "2026-05-01",
                    "checkOut": "2026-05-05",
                    "status": "PENDING"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(bookingJson)
                .when()
                .post("/api/bookings")
                .then()
                .statusCode(200)
                .body("status", equalTo("CONFIRMED"))
                .body("userId", equalTo(1));
    }

    @Test
    void shouldReturnHealthCheck() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/bookings/health")
                .then()
                .statusCode(200)
                .body(containsString("running"));
    }
}