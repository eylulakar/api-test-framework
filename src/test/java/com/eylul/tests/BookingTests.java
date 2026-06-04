package com.eylul.tests;

import com.eylul.base.BaseTest;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class BookingTests extends BaseTest {

    @Test
    void testGetBookingById(){
        given(requestSpec)
                .when()
                    .log().all()
                    .get("/booking/1")
                .then()
                    .statusCode(200)
                    .body("firstname", notNullValue())
                .log().all();

    }

    @Test
    void testGetBookings(){
        given(requestSpec)
                .when()
                    .log().ifValidationFails()
                    .get("/booking")
                .then()
                    .statusCode(200)
                    .body("$", hasSize(greaterThan(0)))
                    .log().ifValidationFails();

    }
}
