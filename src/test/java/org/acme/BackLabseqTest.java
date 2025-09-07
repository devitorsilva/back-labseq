package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class BackLabseqTest {
    @Test
    void testHelloEndpoint() {
        given()
          .when().get("/labseq/1")
          .then()
             .statusCode(200);
    }

}