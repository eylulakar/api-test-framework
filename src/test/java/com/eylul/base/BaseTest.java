package com.eylul.base;

import com.eylul.config.ConfigManager;
import com.eylul.utils.AuthUtils;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    protected static RequestSpecification requestSpec;
    protected static String authToken;

    @BeforeAll
    static void setUp(){
        RestAssured.filters(new AllureRestAssured());

        requestSpec = RestAssured.given()
                .baseUri(ConfigManager.BASE_URL)
                .contentType(ContentType.JSON)
                .log().ifValidationFails();

        authToken = AuthUtils.getToken();
    }
}
