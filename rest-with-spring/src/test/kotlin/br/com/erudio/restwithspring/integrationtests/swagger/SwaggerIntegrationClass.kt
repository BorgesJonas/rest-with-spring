package br.com.erudio.restwithspring.integrationtests.swagger

import br.com.erudio.restwithspring.integrationtests.TestsConfig
import br.com.erudio.restwithspring.integrationtests.testcontainers.AbstractIntegrationTests
import io.restassured.RestAssured
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SwaggerIntegrationClass() : AbstractIntegrationTests() {

    @Test
    fun shouldDisplaySwaggerPage() {
        val content = RestAssured.given()
            .basePath("/swagger-ui/index.html")
            .port(TestsConfig.SERVER_PORT)
            .`when`()
            .get()
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString()

        Assertions.assertTrue(content.contains("Swagger UI"))
    }
}