package com.bbva.hackathon.bbvakids.shop;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ShopResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/api/shop")
          .then()
             .statusCode(200)
             .body(is("hello"));
    }

}