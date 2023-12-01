package br.com.erudio.restwithspring.services

import br.com.erudio.restwithspring.controller.BookController
import br.com.erudio.restwithspring.data.dto.v1.BookDto
import br.com.erudio.restwithspring.entities.Book
import br.com.erudio.restwithspring.exceptions.RequiredObjectIsNullException
import br.com.erudio.restwithspring.exceptions.ResourceNotFoundException
import br.com.erudio.restwithspring.mapper.DozerMapper
import br.com.erudio.restwithspring.repository.BookRepository
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class BookService(
    private val repository: BookRepository
) {
    private val logger = Logger.getLogger(BookService::class.java.name)

    fun findAll(): List<BookDto> {
        logger.info("Finding all books!")
        val books = repository.findAll()
        val vos = DozerMapper.parseListObjects(books, BookDto::class.java)
        for (book in vos) {
            val withSelfRel = linkTo(BookController::class.java).slash(book.id).withSelfRel()
            book.add(withSelfRel)
        }
        return vos
    }

    fun findById(id: Long): BookDto {
        logger.info("Finding one book with ID $id!")
        var book = repository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this ID!") }
        val bookVO: BookDto = DozerMapper.parseObject(book, BookDto::class.java)
        val withSelfRel = linkTo(BookController::class.java).slash(bookVO.id).withSelfRel()
        bookVO.add(withSelfRel)
        return bookVO
    }

    fun create(book: BookDto?) : BookDto{
        if (book == null) throw RequiredObjectIsNullException()
        logger.info("Creating one book with title ${book.title}!")
        var entity: Book = DozerMapper.parseObject(book, Book::class.java)
        val bookVO: BookDto = DozerMapper.parseObject(repository.save(entity), BookDto::class.java)
        val withSelfRel = linkTo(BookController::class.java).slash(bookVO.id).withSelfRel()
        bookVO.add(withSelfRel)
        return bookVO
    }

    fun update(book: BookDto?) : BookDto{
        if (book == null) throw RequiredObjectIsNullException()
        logger.info("Updating one book with ID ${book.id}!")
        val entity = repository.findById(book.id)
            .orElseThrow { ResourceNotFoundException("No records found for this ID!") }

        entity.author = book.author
        entity.title = book.title
        entity.price = book.price
        entity.launchDate = book.launchDate
        val bookVO: BookDto = DozerMapper.parseObject(repository.save(entity), BookDto::class.java)
        val withSelfRel = linkTo(BookController::class.java).slash(bookVO.id).withSelfRel()
        bookVO.add(withSelfRel)
        return bookVO
    }

    fun delete(id: Long) {
        logger.info("Deleting one book with ID $id!")
        val entity = repository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this ID!") }
        repository.delete(entity)
    }
}