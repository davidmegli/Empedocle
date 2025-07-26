package it.unifi.ing.stlab.woodelements.api;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class WoodElementActionTest extends ApiTest {

    @Test
    public void testSplitMergeModifyAndDeleteActions() {
        // Prepare 3 elements for actions
        long id1 = createElement("A");
        long id2 = createElement("B");
        long id3 = createElement("C");

        // Split id1 into id2 and id3
        given()
                .contentType(ContentType.JSON)
                .body(String.format(
                        "{\n" +
                                "  \"sourceId\":%d,\n" +
                                "  \"target1Id\":%d,\n" +
                                "  \"target2Id\":%d\n" +
                                "}", id1, id2, id3))
                .when()
                .post("/actions/split")
                .then()
                .statusCode(200);

        // Merge id2 and id3 into id1
        given()
                .contentType(ContentType.JSON)
                .body(String.format(
                        "{\n" +
                                "  \"sourceId\":%d,\n" +
                                "  \"target1Id\":%d,\n" +
                                "  \"target2Id\":%d\n" +
                                "}", id1, id2, id3))
                .when()
                .post("/actions/merge")
                .then()
                .statusCode(200);

        // Modify: assign source id1 to target id2
        given()
                .contentType(ContentType.JSON)
                .body(String.format(
                        "{\n" +
                                "  \"sourceId\":%d,\n" +
                                "  \"target1Id\":%d\n" +
                                "}", id1, id2))
                .when()
                .post("/actions/modify")
                .then()
                .statusCode(200);

        // Delete action: mark id3
        given()
                .contentType(ContentType.JSON)
                .body(String.format(
                        "{\n" +
                                "  \"sourceId\":%d\n" +
                                "}", id3))
                .when()
                .post("/actions/delete")
                .then()
                .statusCode(200);
    }

    private long createElement(String externalId) {
        return given()
                .contentType(ContentType.JSON)
                .body(String.format(
                        "{\n" +
                                "  \"type\":\"Stem\",\n" +
                                "  \"specie\":\"Specie%s\",\n" +
                                "  \"age\":10,\n" +
                                "  \"placeOfOrigin\":\"Origin%s\",\n" +
                                "  \"externalElementId\":\"%s\",\n" +
                                "  \"note\":\"note\"\n" +
                                "}", externalId, externalId, externalId))
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract()
                .path("id");
    }
}
