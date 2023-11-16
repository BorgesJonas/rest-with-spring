package br.com.erudio.restwithspring.mapper.custom

import br.com.erudio.restwithspring.data.dto.v2.PersonDto
import br.com.erudio.restwithspring.entities.Person
import org.springframework.stereotype.Service
import java.util.*

@Service
class PersonMapper {
    fun mapEntityToDto(person: Person): PersonDto {
        val dto = PersonDto()
        dto.id = person.id
        dto.firstName = person.firstName
        dto.lastName = person.lastName
        dto.address = person.address
        dto.gender = person.gender
        dto.birthDay = Date()

        return dto
    }

    fun mapDtoToEntity(person: PersonDto): Person {
        val entity = Person()
        entity.id = person.id
        entity.firstName = person.firstName
        entity.lastName = person.lastName
        entity.address = person.address
        entity.gender = person.gender
        // entity.birthDay = Date()

        return entity
    }
}