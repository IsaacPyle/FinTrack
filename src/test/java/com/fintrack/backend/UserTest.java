package com.fintrack.backend;

import com.fintrack.backend.model.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

class UserTest extends BaseTest {

    private User user;
    @BeforeEach
    void setup() {
        user = when()
            .post("/users")
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .extract()
            .body()
            .as(User.class);
    }

    // Just to make sure the user gets deleted from the db after the test
    @AfterEach
    void tearDown() {
        given()
            .pathParam("userId", user.getUserId())
            .when()
            .delete("/users/{userId}")
            .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void canCreateAndGetUser() {
        given()
            .pathParam("userId", user.getUserId())
            .when()
            .get("/users/{userId}")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("userId", equalTo(user.getUserId().toString()));
    }

    @Test
    void canDeleteUser() {
        given()
            .pathParam("userId", user.getUserId())
            .when()
            .get("/users/{userId}")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("userId", equalTo(user.getUserId().toString()));

        given()
            .pathParam("userId", user.getUserId())
            .when()
            .delete("/users/{userId}")
            .then()
            .statusCode(HttpStatus.NO_CONTENT.value());

        given()
            .pathParam("userId", user.getUserId())
            .when()
            .get("/users/{userId}")
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void canCreateBudgetForUser() {
        given()
            .pathParam("userId", user.getUserId())
            .when()
            .delete("/users/{userId}")
            .then()
            .statusCode(HttpStatus.NO_CONTENT.value());

        given()
            .pathParam("userId", user.getUserId())
            .when()
            .get("/users/{userId}")
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
