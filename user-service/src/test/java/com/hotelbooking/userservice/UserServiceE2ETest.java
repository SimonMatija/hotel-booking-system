package com.hotelbooking.userservice;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserServiceE2ETest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void shouldGetAllUsers() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/users")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    @Test
    void shouldCreateUser() {
        String userJson = """
                {
                    "firstName": "John",
                    "lastName": "Doe",
                    "email": "john.e2e@example.com",
                    "password": "password123"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(userJson)
                .when()
                .post("/api/users")
                .then()
                .statusCode(200)
                .body("firstName", equalTo("John"))
                .body("email", equalTo("john.e2e@example.com"));
    }

    @Test
    void shouldReturnHealthCheck() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/users/health")
                .then()
                .statusCode(200)
                .body(containsString("running"));
    }
}