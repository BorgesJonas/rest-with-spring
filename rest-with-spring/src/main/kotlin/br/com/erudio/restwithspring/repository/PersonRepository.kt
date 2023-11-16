package br.com.erudio.restwithspring.repository

import br.com.erudio.restwithspring.entities.Person
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonRepository :  JpaRepository<Person, Long?>