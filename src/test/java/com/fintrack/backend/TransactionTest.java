package com.fintrack.backend;

import com.fintrack.backend.model.budget.Budget;
import com.fintrack.backend.model.category.Category;
import com.fintrack.backend.model.transaction.TransactionType;
import com.fintrack.backend.model.user.User;
import com.fintrack.backend.model.transaction.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

public class TransactionTest extends BaseTest {

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

    @Test
    void canCreateTransaction() {
        Category category = Category.builder().name("Grocery").build();

        Transaction transaction = Transaction.builder()
            .categoryId(category.getCategoryId())
            .type(TransactionType.PURCHASE)
            .build();

        given()
            .body(transaction)
        .when()
            .post("/transactions/{userId}")
        .then()
            .statusCode(HttpStatus.CREATED.value())
            .extract()
            .body()
            .as(Transaction.class);

        given()
            .pathParam("userId", user.getUserId())
        .when()
            .get("/users/{userId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("userId", equalTo(user.getUserId().toString()))
            .body("budgetId", equalTo(budget.getBudgetId().toString()))
            .body("transactionIds", notNullValue());
    }
}
