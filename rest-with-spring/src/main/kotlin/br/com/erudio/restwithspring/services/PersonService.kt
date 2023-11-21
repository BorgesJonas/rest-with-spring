package br.com.erudio.restwithspring.services

import br.com.erudio.restwithspring.data.dto.v1.PersonDto
import br.com.erudio.restwithspring.entities.Person
import br.com.erudio.restwithspring.exceptions.ResourceNotFoundException
import br.com.erudio.restwithspring.mapper.DozerMapper
import br.com.erudio.restwithspring.repository.PersonRepository
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

        return DozerMapper.parseObject(person, PersonDto::class.java)
    }

    fun findAll(): ArrayList<PersonDto> {
        logger.info("Searching all people!")
        val persons = personRepository.findAll().toList()
        return DozerMapper.parseListObjects(persons, PersonDto::class.java)
    }

    fun createPerson(person: PersonDto): PersonDto {
        logger.info("Creating person with name ${person.firstName}")
        val entity: Person = DozerMapper.parseObject(person, Person::class.java)
        return DozerMapper.parseObject(personRepository.save(entity), PersonDto::class.java)
    }

    fun updatePerson(person: PersonDto): PersonDto {
        logger.info("Updating person with name ${person.firstName}")
        if (!personRepository.existsById(person.id))
            throw ResourceNotFoundException("User not found!")

        val entity: Person = DozerMapper.parseObject(person, Person::class.java)
        return DozerMapper.parseObject(personRepository.save(entity), PersonDto::class.java)
    }

    fun deletePerson(id: Long) {
        logger.info("Deleting person with ID $id")
        val person = personRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("User not found!") }
        personRepository.delete(person)
    }
}