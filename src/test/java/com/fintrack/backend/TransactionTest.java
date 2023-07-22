package com.fintrack.backend;

import com.fintrack.backend.model.budget.Budget;
import com.fintrack.backend.model.category.Category;
import com.fintrack.backend.model.transaction.TransactionType;
import com.fintrack.backend.model.user.User;
import com.fintrack.backend.model.transaction.Transaction;
import com.fintrack.backend.service.TransactionService;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.equalTo;

class TransactionTest extends BaseTest {

    private User user;
    private Budget budget;
    private Transaction transaction;
    private Category category;

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

        category = Category.builder()
            .categoryId(UUID.randomUUID())
            .name("Grocery")
            .build();

        transaction = Transaction.builder()
            .transactionId(UUID.randomUUID())
            .categoryId(category.getCategoryId().toString())
            .amount(BigDecimal.valueOf(69))
            .transactionDate(Date.from(Instant.now()))
            .type(TransactionType.PURCHASE)
            .build();


    }

    @AfterEach
    void tearDown() {
        given()
            .pathParam("userId", user.getUserId())
            .pathParam("transactionId", transaction.getTransactionId())
        .when()
            .delete("/transactions/{userId}/{transactionId}")
        .then()
            .statusCode(HttpStatus.NO_CONTENT.value());

        given()
            .pathParam("userId", user.getUserId())
        .when()
            .delete("/users/{userId}")
        .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void createdTransactionIsAddedToBudget() {
        given()
            .pathParam("budgetId", budget.getBudgetId())
        .when()
            .get("/budgets/{budgetId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("budgetId", equalTo(budget.getBudgetId().toString()))
            .body("transactionIds", nullValue());

        given()
            .body(transaction)
            .pathParam("userId", user.getUserId())
            .contentType(ContentType.APPLICATION_JSON.getMimeType())
        .when()
            .post("/transactions/{userId}")
        .then()
            .statusCode(HttpStatus.CREATED.value());

        given()
            .pathParam("budgetId", budget.getBudgetId())
        .when()
            .get("/budgets/{budgetId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("budgetId", equalTo(budget.getBudgetId().toString()))
            .body("transactionIds[0]", equalTo(transaction.getTransactionId().toString()));
    }

    @Test
    void canGetTransactionById() {
        given()
            .pathParam("transactionId", transaction.getTransactionId())
        .when()
            .get("/transaction/{transactionId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());

        Transaction createdTransaction = given()
            .body(transaction)
            .pathParam("userId", user.getUserId())
            .contentType(ContentType.APPLICATION_JSON.getMimeType())
        .when()
            .post("/transactions/{userId}")
        .then()
            .statusCode(HttpStatus.CREATED.value())
            .extract()
            .body()
            .as(Transaction.class);

        given()
            .pathParam("transactionId", createdTransaction.getTransactionId())
        .when()
            .get("/transactions/{transactionId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("transactionId", equalTo(createdTransaction.getTransactionId().toString()))
            .body("categoryId", equalTo(createdTransaction.getCategoryId()));
    }
}
