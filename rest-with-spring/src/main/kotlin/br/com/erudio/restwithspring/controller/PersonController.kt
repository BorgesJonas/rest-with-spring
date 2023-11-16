package br.com.erudio.restwithspring.controller

import br.com.erudio.restwithspring.data.dto.v1.PersonDto
import br.com.erudio.restwithspring.data.dto.v2.PersonDto as PersonDtoV2
import br.com.erudio.restwithspring.services.PersonService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("person")
class PersonController(
    private val personService: PersonService
) {

   @GetMapping
    fun findById(): List<PersonDto> {
        return personService.findAll()
    }

    @GetMapping("/{id}")
    fun findAll(@PathVariable id: Long): PersonDto? {
        return personService.findById(id)
    }

    @PostMapping
    fun create(@RequestBody person: PersonDto): PersonDto {
        return personService.createPerson(person)
    }

    @PostMapping("/v2")
    fun createV2(@RequestBody person: PersonDtoV2): PersonDtoV2 {
        return personService.createPersonV2(person)
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