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
                .body("""
            {
              "sourceId":%d,
              "target1Id":%d,
              "target2Id":%d
            }
          """.formatted(id1, id2, id3))
                .when()
                .post("/actions/split")
                .then()
                .statusCode(200);

        // Merge id2 and id3 into id1
        given()
                .contentType(ContentType.JSON)
                .body("""
            {
              "targetId":%d,
              "source1Id":%d,
              "source2Id":%d
            }
          """.formatted(id1, id2, id3))
                .when()
                .post("/actions/merge")
                .then()
                .statusCode(200);

        // Modify: assign source id1 to target id2
        given()
                .contentType(ContentType.JSON)
                .body("""
            {
              "sourceId":%d,
              "targetId":%d
            }
          """.formatted(id1, id2))
                .when()
                .post("/actions/modify")
                .then()
                .statusCode(200);

        // Delete action: mark id3
        given()
                .contentType(ContentType.JSON)
                .body("""
            { "sourceId":%d }
          """.formatted(id3))
                .when()
                .post("/actions/delete")
                .then()
                .statusCode(200);
    }

    private long createElement(String externalId) {
        return given()
                .contentType(ContentType.JSON)
                .body("""
                {
                  "type":"Stem",
                  "specie":"Specie%s",
                  "age":10,
                  "placeOfOrigin":"Origin%s",
                  "externalElementId":"%s",
                  "note":"note"
                }
            """.formatted(externalId, externalId, externalId))
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract()
                .path("id");
    }
}
