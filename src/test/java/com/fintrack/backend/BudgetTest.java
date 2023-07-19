package com.fintrack.backend;

import com.fintrack.backend.model.budget.Budget;
import com.fintrack.backend.model.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

class BudgetTest extends BaseTest {

    private User user;
    private Budget budget;

    @BeforeEach
    void setup() {
        user = when()
            .post("/users")
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .extract()
            .body()
            .as(User.class);

        budget = given()
            .pathParam("userId", user.getUserId())
            .when()
            .post("/budgets/{userId}")
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .extract()
            .body()
            .as(Budget.class);
    }

    // Just to make sure resources deleted from the db after the test
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
    void canGetBudget() {
        given()
            .pathParam("userId", user.getUserId())
        .when()
            .get("/users/{userId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .log().all()
            .body("userId", equalTo(user.getUserId().toString()))
            .body("budgetId", equalTo(budget.getBudgetId().toString()));
    }

    @Test
    void canDeleteBudget() {
        UUID budgetId = budget.getBudgetId();

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
            .pathParam("budgetId", budgetId)
            .when()
            .get("/budgets/{budgetId}")
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
