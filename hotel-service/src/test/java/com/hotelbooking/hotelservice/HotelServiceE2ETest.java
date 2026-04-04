package com.hotelbooking.hotelservice;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HotelServiceE2ETest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void shouldGetAllHotels() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/hotels")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    @Test
    void shouldCreateHotel() {
        String hotelJson = """
                {
                    "name": "Hotel Test",
                    "location": "Belgrade",
                    "description": "Test hotel",
                    "stars": 4
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(hotelJson)
                .when()
                .post("/api/hotels")
                .then()
                .statusCode(200)
                .body("name", equalTo("Hotel Test"))
                .body("location", equalTo("Belgrade"));
    }

    @Test
    void shouldReturnHealthCheck() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/hotels/health")
                .then()
                .statusCode(200)
                .body(containsString("running"));
    }
}