package com.fintrack.backend;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public abstract class BaseTest {

	@LocalServerPort
	private int serverPort;

	@BeforeEach
	public void setUp() {
		RestAssured.port = serverPort;
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	}
}
