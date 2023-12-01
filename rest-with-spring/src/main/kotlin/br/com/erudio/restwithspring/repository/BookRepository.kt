package br.com.erudio.restwithspring.repository

import br.com.erudio.restwithspring.entities.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : JpaRepository<Book, Long?>