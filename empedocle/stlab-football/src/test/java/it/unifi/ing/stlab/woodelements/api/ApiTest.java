package it.unifi.ing.stlab.woodelements.api;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class ApiTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = Integer.getInteger("test.port", 8080);
        RestAssured.basePath = "/api/woodelements";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
