package br.com.erudio.restwithspring.unittests.services.mapper.mocks

import br.com.erudio.restwithspring.data.dto.v1.BookDto
import br.com.erudio.restwithspring.entities.Book

class MockBook {

    fun mockEntityList(): ArrayList<Book> {
        val books: ArrayList<Book> = ArrayList<Book>()
        for (i in 0..13) {
            books.add(mockEntity(i))
        }
        return books
    }

    fun mockDtoList(): ArrayList<BookDto> {
        val books: ArrayList<BookDto> = ArrayList()
        for (i in 0..13) {
            books.add(mockDto(i))
        }
        return books
    }

    fun mockEntity(number: Int) = Book(
            id = number.toLong(),
            author = "Some Author$number",
            price = 25.0,
            title = "Some Title$number"
        )

    fun mockDto(number: Int) = BookDto(
            id = number.toLong(),
            author = "Some Author$number",
            price = 25.0,
            title = "Some Title$number"
        )
}