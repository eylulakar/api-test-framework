package com.eylul.base;

import com.eylul.config.ConfigManager;
import com.eylul.utils.AuthUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    protected static RequestSpecification requestSpec;
    protected static String authToken;

    @BeforeAll
    static void setUp(){
        requestSpec = RestAssured.given()
                .baseUri(ConfigManager.BASE_URL)
                .contentType(ContentType.JSON)
                .log().ifValidationFails();

        authToken = AuthUtils.getToken();
    }
}
