package br.com.erudio.restwithspring.controller

import br.com.erudio.restwithspring.data.dto.v1.PersonDto
import br.com.erudio.restwithspring.services.PersonService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/person/v1")
class PersonController(
    private val personService: PersonService
) {

   @GetMapping
    fun findAll(): List<PersonDto> {
        return personService.findAll()
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): PersonDto? {
        return personService.findById(id)
    }

    @PostMapping
    fun create(@RequestBody person: PersonDto): PersonDto {
        return personService.createPerson(person)
    }

    @PutMapping
    fun update(@RequestBody person: PersonDto): PersonDto {
        return personService.updatePerson(person)
    }

    @DeleteMapping("/{id}")
    fun update(@PathVariable id: Long): ResponseEntity<Any> {
        personService.deletePerson(id)
        return ResponseEntity.noContent().build<Any>()
    }
}