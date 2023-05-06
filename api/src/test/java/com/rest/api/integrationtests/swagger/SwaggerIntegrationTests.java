package com.rest.api.integrationtests.swagger;

import com.rest.api.configs.TestConfigs;
import com.rest.api.integrationtests.testcontainers.AbstractIntegrationTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerIntegrationTests extends AbstractIntegrationTests {

    @Test
    public void shouldDisplaySwaggerUiPage() {
        var content =
                given().
                        basePath("/swagger-ui/index.html")
                        .port(TestConfigs.SERVER_PORT)
                        .when()
                        .get()
                        .then()
                        .statusCode(200)
                        .extract().body().asString();
		Assertions.assertTrue(content.contains("Swagger UI"));
	}

}
