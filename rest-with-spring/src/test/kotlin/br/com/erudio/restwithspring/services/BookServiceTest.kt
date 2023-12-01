package br.com.erudio.restwithspring.services

import br.com.erudio.restwithspring.exceptions.RequiredObjectIsNullException
import br.com.erudio.restwithspring.mapper.mocks.MockBook
import br.com.erudio.restwithspring.repository.BookRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*


@ExtendWith(MockitoExtension::class)
internal class BookServiceTest {
    @InjectMocks
    private lateinit var service: BookService

    @Mock
    private lateinit var repository: BookRepository

    @Test
    fun findAll() {
        val list = MockBook().mockEntityList()
        `when`(repository.findAll()).thenReturn(list)

        val books = service.findAll()

        assertNotNull(books)
        assertEquals(14, books.size)

        val bookOne = books[1]

        assertNotNull(bookOne)
        assertNotNull(bookOne.id)
        assertNotNull(bookOne.links)
        assertTrue(bookOne.links.toString().contains("</api/book/v1/1>;rel=\"self\""))
        assertEquals("Some Title1", bookOne.title)
        assertEquals("Some Author1", bookOne.author)
        assertEquals(25.0, bookOne.price)

        val bookFour = books[4]

        assertNotNull(bookFour)
        assertNotNull(bookFour.id)
        assertNotNull(bookFour.links)
        assertTrue(bookFour.links.toString().contains("</api/book/v1/4>;rel=\"self\""))
        assertEquals("Some Title4", bookFour.title)
        assertEquals("Some Author4", bookFour.author)
        assertEquals(25.0, bookFour.price)

        val bookSeven = books[7]

        assertNotNull(bookSeven)
        assertNotNull(bookSeven.id)
        assertNotNull(bookSeven.links)
        assertTrue(bookSeven.links.toString().contains("</api/book/v1/7>;rel=\"self\""))
        assertEquals("Some Title7", bookSeven.title)
        assertEquals("Some Author7", bookSeven.author)
        assertEquals(25.0, bookSeven.price)
    }

    @Test
    fun findById() {
        val book = MockBook().mockEntity(1)
        book.id = 1
        `when`(repository.findById(1)).thenReturn(Optional.of(book))

        val result = service.findById(1)

        assertNotNull(result)
        assertNotNull(result.id)
        assertNotNull(result.links)
        assertTrue(result.links.toString().contains("</api/book/v1/1>;rel=\"self\""))
        assertEquals("Some Title1", result.title)
        assertEquals("Some Author1", result.author)
        assertEquals(25.0, result.price)
    }

    @Test
    fun create() {
        val entity = MockBook().mockEntity(1)

        val persisted = entity.copy()
        persisted.id = 1

        `when`(repository.save(entity)).thenReturn(persisted)

        val dto = MockBook().mockDto(1)
        val result = service.create(dto)

        assertNotNull(result)
        assertNotNull(result.id)
        assertNotNull(result.links)
        assertTrue(result.links.toString().contains("</api/book/v1/1>;rel=\"self\""))
        assertEquals("Some Title1", result.title)
        assertEquals("Some Author1", result.author)
        assertEquals(25.0, result.price)
    }

    @Test
    fun createWithNullBook() {
        val exception: Exception = assertThrows(
            RequiredObjectIsNullException::class.java
        ) {
            service.create(null)
        }

        val expectedMessage = "It is not allowed to persist a null object!"
        val actualMessage = exception.message
        assertTrue(actualMessage!!.contains(expectedMessage))
    }

    @Test
    fun update() {
        val entity = MockBook().mockEntity(1)

        val persisted = entity.copy()
        persisted.id = 1

        `when`(repository.findById(1)).thenReturn(Optional.of(entity))
        `when`(repository.save(entity)).thenReturn(persisted)

        val dto = MockBook().mockDto(1)
        val result = service.update(dto)

        assertNotNull(result)
        assertNotNull(result.id)
        assertNotNull(result.links)
        assertTrue(result.links.toString().contains("</api/book/v1/1>;rel=\"self\""))
        assertEquals("Some Title1", result.title)
        assertEquals("Some Author1", result.author)
        assertEquals(25.0, result.price)
    }

    @Test
    fun updateWithNullBook() {
        val exception: Exception = assertThrows(
            RequiredObjectIsNullException::class.java
        ) {
            service.update(null)
        }

        val expectedMessage = "It is not allowed to persist a null object!"
        val actualMessage = exception.message
        assertTrue(actualMessage!!.contains(expectedMessage))
    }

    @Test
    fun delete() {
        val entity = MockBook().mockEntity(1)
        `when`(repository.findById(1)).thenReturn(Optional.of(entity))
        service.delete(1)
    }
}