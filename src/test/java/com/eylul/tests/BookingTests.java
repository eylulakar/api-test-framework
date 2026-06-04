package com.eylul.tests;

import com.eylul.base.BaseTest;
import com.eylul.models.Booking;
import com.eylul.models.CreateBookingResponse;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookingTests extends BaseTest {

    private static int createdBookingId;

    @Test
    @Order(1)
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
    @Order(2)
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

    @Test
    @Order(3)
    void testCreateBooking(){
        String requestBody = """
                {
                    "firstname" : "Eylul",
                    "lastname" : "Akar",
                    "totalprice" : 1500,
                    "depositpaid" : true,
                    "bookingdates" : {
                        "checkin" : "2026-01-01",
                        "checkout" : "2026-01-03"
                    },
                    "additionalneeds" : "Breakfast"
                }
                """;

        CreateBookingResponse response =
                given(requestSpec)
                     .body(requestBody)
                .when()
                    .log().all()
                    .post("/booking")
                .then()
                    .log().all()
                    .statusCode(200)
                    .extract().as(CreateBookingResponse.class);

        assertNotNull(response);
        assertNotNull(response.booking);
        assertEquals("Eylul", response.booking.getFirstname());
        assertEquals("Akar", response.booking.getLastName());
        assertEquals(1500, response.booking.getTotalPrice());
        assertEquals("Breakfast", response.booking.getAdditionalNeeds());
        assertTrue(response.booking.isDepositPaid());
        assertNotNull(response.booking.getBookingDates());
        assertEquals("2026-01-01", response.booking.getBookingDates().getCheckIn());
        assertEquals("2026-01-03", response.booking.getBookingDates().getCheckOut());

        createdBookingId = response.bookingid;
    }

    @Test
    @Order(4)
    void testUpdateBooking(){
        String requestBody = """
                {
                    "firstname" : "Eylul updated",
                    "lastname" : "Akar updated",
                    "totalprice" : 1600,
                    "depositpaid" : false,
                    "bookingdates" : {
                        "checkin" : "2027-01-01",
                        "checkout" : "2027-01-03"
                    },
                    "additionalneeds" : "Breakfast updated"
                }
                """;

        Booking response =
                given(requestSpec)
                        .cookie("token", authToken)
                            .body(requestBody)
                        .when()
                            .log().all()
                            .put("/booking/" + createdBookingId)
                        .then()
                            .log().all()
                            .statusCode(200)
                            .extract().as(Booking.class);

        assertNotNull(response);
        assertEquals("Eylul updated", response.getFirstname());
        assertEquals("Akar updated", response.getLastName());
        assertEquals(1600, response.getTotalPrice());
        assertEquals("Breakfast updated", response.getAdditionalNeeds());
        assertFalse(response.isDepositPaid());
        assertNotNull(response.getBookingDates());
        assertEquals("2027-01-01", response.getBookingDates().getCheckIn());
        assertEquals("2027-01-03", response.getBookingDates().getCheckOut());
    }

    @Test
    @Order(5)
    void testDeleteBookingById(){
        given(requestSpec)
                .when()
                    .log().all()
                    .cookie("token", authToken)
                    .delete("/booking/" + createdBookingId)
                .then()
                    .statusCode(201)
                    .log().all();

        given(requestSpec)
                .when()
                    .log().all()
                    .get("/booking/" + createdBookingId)
                .then()
                    .statusCode(404)
                    .log().all();

    }
}
