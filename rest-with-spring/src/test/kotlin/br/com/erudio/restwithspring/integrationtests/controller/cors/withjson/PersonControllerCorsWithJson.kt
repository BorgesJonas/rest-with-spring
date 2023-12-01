package br.com.erudio.restwithspring.integrationtests.controller.cors.withjson

import br.com.erudio.restwithspring.integrationtests.TestsConfig
import br.com.erudio.restwithspring.integrationtests.dto.PersonDto
import br.com.erudio.restwithspring.integrationtests.testcontainers.AbstractIntegrationTests
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.filter.log.LogDetail
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.specification.RequestSpecification
import org.junit.jupiter.api.*
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonControllerCorsWithJson() : AbstractIntegrationTests() {

    private lateinit var specification: RequestSpecification
    private lateinit var objectMapper: ObjectMapper
    private lateinit var personDto: PersonDto

    @BeforeAll
    fun setUpTests() {
        objectMapper = ObjectMapper()
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        personDto = PersonDto()
    }


    @Test
    @Order(1)
    fun create() {
        mockPerson()
        specification = RequestSpecBuilder()
            .addHeader(
                TestsConfig.HEADER_PARAM_ORIGIN,
                TestsConfig.ORIGIN_REACT_APP
            )
            .setBasePath("/api/person/v1")
            .setPort(TestsConfig.SERVER_PORT)
            .addFilter(RequestLoggingFilter(LogDetail.ALL))
            .addFilter(ResponseLoggingFilter(LogDetail.ALL))
            .build()

        val content = RestAssured.given()
            .spec(specification)
            .contentType(TestsConfig.CONTENT_TYPE_JSON)
            .body(personDto)
            .`when`()
            .post()
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString()

        val createdPerson = objectMapper.readValue(
            content,
            PersonDto::class.java
        )

        personDto = createdPerson

        Assertions.assertNotNull(createdPerson.id)
        Assertions.assertNotNull(createdPerson.firstName)
        Assertions.assertNotNull(createdPerson.lastName)
        Assertions.assertNotNull(createdPerson.address)
        Assertions.assertNotNull(createdPerson.gender)
        Assertions.assertEquals("João", createdPerson.firstName)
        Assertions.assertEquals("Da Silva", createdPerson.lastName)
        Assertions.assertEquals("London - England", createdPerson.address)
        Assertions.assertEquals("Male", createdPerson.gender)
    }

    @Test
    @Order(2)
    fun testWithWrongOrigin() {
        mockPerson()
        specification = RequestSpecBuilder()
            .addHeader(
                TestsConfig.HEADER_PARAM_ORIGIN,
                TestsConfig.ORIGIN_ERUDIO
            )
            .setBasePath("/api/person/v1")
            .setPort(TestsConfig.SERVER_PORT)
            .addFilter(RequestLoggingFilter(LogDetail.ALL))
            .addFilter(ResponseLoggingFilter(LogDetail.ALL))
            .build()

        val content = RestAssured.given()
            .spec(specification)
            .contentType(TestsConfig.CONTENT_TYPE_JSON)
            .body(personDto)
            .`when`()
            .post()
            .then()
            .statusCode(403)
            .extract()
            .body()
            .asString()

        Assertions.assertEquals("Invalid CORS request", content)
    }

    @Test
    @Order(3)
    fun findById() {
        mockPerson()
        specification = RequestSpecBuilder()
            .addHeader(
                TestsConfig.HEADER_PARAM_ORIGIN,
                TestsConfig.ORIGIN_REACT_APP
            )
            .setBasePath("/api/person/v1")
            .setPort(TestsConfig.SERVER_PORT)
            .addFilter(RequestLoggingFilter(LogDetail.ALL))
            .addFilter(ResponseLoggingFilter(LogDetail.ALL))
            .build()

        val content = RestAssured.given()
            .spec(specification)
            .contentType(TestsConfig.CONTENT_TYPE_JSON)
            .pathParam("id", personDto.id)
            .`when`()["{id}"]
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString()

        val createdPerson = objectMapper.readValue(
            content,
            PersonDto::class.java
        )

        Assertions.assertNotNull(createdPerson.id)
        Assertions.assertNotNull(createdPerson.firstName)
        Assertions.assertNotNull(createdPerson.lastName)
        Assertions.assertNotNull(createdPerson.address)
        Assertions.assertNotNull(createdPerson.gender)
        Assertions.assertEquals("João", createdPerson.firstName)
        Assertions.assertEquals("Da Silva", createdPerson.lastName)
        Assertions.assertEquals("London - England", createdPerson.address)
        Assertions.assertEquals("Male", createdPerson.gender)
    }

    @Test
    @Order(4)
    fun findByIdWithWrongOrigin() {
        mockPerson()
        specification = RequestSpecBuilder()
            .addHeader(
                TestsConfig.HEADER_PARAM_ORIGIN,
                TestsConfig.ORIGIN_ERUDIO
            )
            .setBasePath("/api/person/v1")
            .setPort(TestsConfig.SERVER_PORT)
            .addFilter(RequestLoggingFilter(LogDetail.ALL))
            .addFilter(ResponseLoggingFilter(LogDetail.ALL))
            .build()

        val content = RestAssured.given()
            .spec(specification)
            .contentType(TestsConfig.CONTENT_TYPE_JSON)
            .pathParam("id", personDto.id)
            .`when`()["{id}"]
            .then()
            .statusCode(403)
            .extract()
            .body()
            .asString()

        Assertions.assertEquals("Invalid CORS request", content)
    }

    private fun mockPerson() {
        personDto.firstName = "João"
        personDto.lastName = "Da Silva"
        personDto.address = "London - England"
        personDto.gender = "Male"
    }
}