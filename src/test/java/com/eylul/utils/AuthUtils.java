package com.eylul.utils;

import com.eylul.config.ConfigManager;
import com.eylul.models.AuthCredentials;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class AuthUtils {

    public static String getToken() {
        AuthCredentials authCredentials =  new AuthCredentials(ConfigManager.AUTH_USERNAME, ConfigManager.AUTH_PASSWORD);
        return RestAssured
                .given()
                    .baseUri(ConfigManager.BASE_URL)
                    .contentType(ContentType.JSON)
                    .body(authCredentials)
                    .log().all()
                .when()
                    .post("/auth")
                .then()
                    .log().all()
                    .statusCode(200)
                    .extract()
                    .path("token");
    }
}
