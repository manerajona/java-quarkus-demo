package com.github.manerajona;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class MaskResourceTest {

    @Test
    void testMaskEndpoint_Success() {
        given()
                .when().get("/mask/5555555555554444")
                .then()
                .statusCode(200)
                .body(is("XXXXXXXXXXXX4444"));
    }

    @Test
    void testMaskEndpoint_Fail() {
        given()
                .when().get("/mask/555555555555")
                .then()
                .statusCode(400)
                .body(is("Invalid credit card."));
    }
}