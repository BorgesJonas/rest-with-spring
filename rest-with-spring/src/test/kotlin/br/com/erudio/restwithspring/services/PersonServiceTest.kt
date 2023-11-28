package br.com.erudio.restwithspring.services

import br.com.erudio.restwithspring.mapper.mocks.MockPerson
import br.com.erudio.restwithspring.repository.PersonRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock

import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
internal class PersonServiceTest {
    @InjectMocks
    private lateinit var service: PersonService

    @Mock
    private lateinit var repository: PersonRepository


    @Test
    fun findAll() {
        val list = MockPerson().mockEntityList()
        `when`(repository.findAll()).thenReturn(list)

        val persons = service.findAll()

        assertNotNull(persons)
        assertEquals(14, persons.size)

        val personOne = persons[1]

        assertNotNull(personOne)
        assertNotNull(personOne.id)
        assertNotNull(personOne.links)
        assertTrue(personOne.links.toString().contains("</api/person/v1/1>;rel=\"self\""))
        assertEquals("Address Test1", personOne.address)
        assertEquals("First Name Test1", personOne.firstName)
        assertEquals("Last Name Test1", personOne.lastName)
        assertEquals("Female", personOne.gender)

        val personFour = persons[4]

        assertNotNull(personFour)
        assertNotNull(personFour.id)
        assertNotNull(personFour.links)
        assertTrue(personFour.links.toString().contains("</api/person/v1/4>;rel=\"self\""))
        assertEquals("Address Test4", personFour.address)
        assertEquals("First Name Test4", personFour.firstName)
        assertEquals("Last Name Test4", personFour.lastName)
        assertEquals("Male", personFour.gender)

        val personSeven = persons[7]

        assertNotNull(personSeven)
        assertNotNull(personSeven.id)
        assertNotNull(personSeven.links)
        assertTrue(personSeven.links.toString().contains("</api/person/v1/7>;rel=\"self\""))
        assertEquals("Address Test7", personSeven.address)
        assertEquals("First Name Test7", personSeven.firstName)
        assertEquals("Last Name Test7", personSeven.lastName)
        assertEquals("Female", personSeven.gender)
    }

    @Test
    fun findById() {
        val person = MockPerson().mockEntity(1)
        person.id = 1
        `when`(repository.findById(1)).thenReturn(Optional.of(person))

        val result = service.findById(1)

        assertNotNull(result)
        assertNotNull(result.id)
        assertNotNull(result.links)
        assertTrue(result.links.toString().contains("</api/person/v1/1>;rel=\"self\""))
        assertEquals("Address Test1", result.address)
        assertEquals("First Name Test1", result.firstName)
        assertEquals("Last Name Test1", result.lastName)
        assertEquals("Female", result.gender)
    }

    @Test
    fun create() {
        val entity = MockPerson().mockEntity(1)

        val persisted = entity.copy()
        persisted.id = 1

        `when`(repository.save(entity)).thenReturn(persisted)

        val dto = MockPerson().mockDto(1)
        val result = service.createPerson(dto)

        assertNotNull(result)
        assertNotNull(result.id)
        assertNotNull(result.links)
        assertTrue(result.links.toString().contains("</api/person/v1/1>;rel=\"self\""))
        assertEquals("Address Test1", result.address)
        assertEquals("First Name Test1", result.firstName)
        assertEquals("Last Name Test1", result.lastName)
        assertEquals("Female", result.gender)
    }

    /** @Test
    fun createWithNullPerson() {
        val exception: Exception = assertThrows(
            RequiredObjectIsNullException::class.java
        ) {
            service.createPerson(null)
        }

        val expectedMessage = "It is not allowed to persist a null object!"
        val actualMessage = exception.message
        assertTrue(actualMessage!!.contains(expectedMessage))
    } */

    @Test
    fun update() {
        val entity = MockPerson().mockEntity(1)

        val persisted = entity.copy()
        persisted.id = 1

        `when`(repository.existsById(1)).thenReturn(true)
        `when`(repository.save(entity)).thenReturn(persisted)

        val dto = MockPerson().mockDto(1)
        val result = service.updatePerson(dto)

        assertNotNull(result)
        assertNotNull(result.id)
        assertNotNull(result.links)
        assertTrue(result.links.toString().contains("</api/person/v1/1>;rel=\"self\""))
        assertEquals("Address Test1", result.address)
        assertEquals("First Name Test1", result.firstName)
        assertEquals("Last Name Test1", result.lastName)
        assertEquals("Female", result.gender)
    }

    /** @Test
    fun updateWithNullPerson() {
        val exception: Exception = assertThrows(
            RequiredObjectIsNullException::class.java
        ) {
            service.updatePerson(null)
        }

        val expectedMessage = "It is not allowed to persist a null object!"
        val actualMessage = exception.message
        assertTrue(actualMessage!!.contains(expectedMessage))
    } */

    @Test
    fun delete() {
        val entity = MockPerson().mockEntity(1)
        `when`(repository.findById(1)).thenReturn(Optional.of(entity))
        service.deletePerson(1)
    }
}