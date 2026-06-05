package com.eylul.tests;

import com.eylul.base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class NegativeBookingTests extends BaseTest {

    private static int createdBookingId;

    @Test
    @DisplayName("DELETE without auth token returns 403")
    void testGetNonExistentBookingById(){
        given(requestSpec)
                .when()
                    .log().all()
                    .get("/booking/99999")
                .then()
                    .statusCode(404)
                .log().all();
    }

    @Test
    @DisplayName("POST with empty body returns 500")
    void testCreateBookingWithInvalidBody(){
        String requestBody = """
                {}
                """;

        given(requestSpec)
                     .body(requestBody)
                .when()
                    .log().all()
                    .post("/booking")
                .then()
                    .log().all()
                    .statusCode(500)
                    .body(equalTo("Internal Server Error"));
    }

    @Test
    @DisplayName("GET non-existent booking ID returns 404")
    void testDeleteBookingWithoutAuth(){
        given(requestSpec)
                .when()
                    .log().all()
                    .delete("/booking/1")
                .then()
                    .statusCode(403)
                    .log().all();

    }
}
