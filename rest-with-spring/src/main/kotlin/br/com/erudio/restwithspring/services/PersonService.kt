package br.com.erudio.restwithspring.services

import br.com.erudio.restwithspring.controller.PersonController
import br.com.erudio.restwithspring.data.dto.v1.PersonDto
import br.com.erudio.restwithspring.entities.Person
import br.com.erudio.restwithspring.exceptions.RequiredObjectIsNullException
import br.com.erudio.restwithspring.exceptions.ResourceNotFoundException
import br.com.erudio.restwithspring.mapper.DozerMapper
import br.com.erudio.restwithspring.repository.PersonRepository
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import java.util.logging.Logger
import kotlin.collections.ArrayList

@Service
class PersonService(
    private val personRepository: PersonRepository,
) {
    private val logger = Logger.getLogger(PersonService::class.java.name)

    fun findById(id: Long): PersonDto {
        val person = personRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("User not found!") }

        val personDto = DozerMapper.parseObject(person, PersonDto::class.java)
        return personDto.add(linkTo(PersonController::class.java).slash(personDto.id).withSelfRel())
    }

    fun findAll(): ArrayList<PersonDto> {
        logger.info("Searching all people!")
        val persons = personRepository.findAll().toList()
        val personsDto = DozerMapper.parseListObjects(persons, PersonDto::class.java)
        personsDto.forEach { it.add(linkTo(PersonController::class.java).slash(it.id).withSelfRel()) }
        return personsDto
    }

    fun createPerson(person: PersonDto?): PersonDto {
        if (person == null) throw RequiredObjectIsNullException()
        logger.info("Creating person with name ${person.firstName}")
        val entity: Person = DozerMapper.parseObject(person, Person::class.java)
        val personDto = DozerMapper.parseObject(personRepository.save(entity), PersonDto::class.java)
        return personDto.add(linkTo(PersonController::class.java).slash(personDto.id).withSelfRel())
    }

    fun updatePerson(person: PersonDto?): PersonDto {
        if (person == null) throw RequiredObjectIsNullException()
        logger.info("Updating person with name ${person.firstName}")
        if (!personRepository.existsById(person.id))
            throw ResourceNotFoundException("User not found!")

        val entity: Person = DozerMapper.parseObject(person, Person::class.java)
        val personDto = DozerMapper.parseObject(personRepository.save(entity), PersonDto::class.java)
        return personDto.add(linkTo(PersonController::class.java).slash(personDto.id).withSelfRel())
    }

    fun deletePerson(id: Long) {
        logger.info("Deleting person with ID $id")
        val person = personRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("User not found!") }
        personRepository.delete(person)
    }
}