package it.unifi.ing.stlab.woodelements.api;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class WoodElementCrudTest extends ApiTest {

    @Test
    public void testCreateAndGetAndDelete() {
        // Create a new wood element
        String specie = "Oak";
        int age = 50;
        String place = "Forest";

        String json = String.format(
                "{\n" +
                        "  \"type\":\"Tree\",\n" +
                        "  \"specie\":\"%s\",\n" +
                        "  \"age\":%d,\n" +
                        "  \"placeOfOrigin\":\"%s\",\n" +
                        "  \"externalElementId\":\"E1\",\n" +
                        "  \"note\":\"Test note\"\n" +
                        "}", specie, age, place);
        long id = given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post()
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("specie", equalTo(specie),
                        "age", equalTo(age),
                        "externalElementId", equalTo("E1"))
                .extract()
                .path("id");

        // Retrieve it
        when()
                .get("/{id}", id)
                .then()
                .statusCode(200)
                .body("id", equalTo((int)id),
                        "specie", equalTo(specie));

        // Delete it
        when()
                .delete("/{id}", id)
                .then()
                .statusCode(204);

        // Confirm it's gone
        when()
                .get("/{id}", id)
                .then()
                .statusCode(404);
    }
}
