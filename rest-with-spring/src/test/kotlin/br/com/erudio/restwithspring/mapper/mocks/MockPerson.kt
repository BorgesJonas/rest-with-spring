package br.com.erudio.restwithspring.mapper.mocks

import br.com.erudio.restwithspring.data.dto.v1.PersonDto
import br.com.erudio.restwithspring.entities.Person

class MockPerson {
    fun mockEntityList(): ArrayList<Person> {
        val persons: ArrayList<Person> = ArrayList<Person>()
        for (i in 0..13) {
            persons.add(mockEntity(i))
        }
        return persons
    }

    fun mockDtoList(): ArrayList<PersonDto> {
        val persons: ArrayList<PersonDto> = ArrayList()
        for (i in 0..13) {
            persons.add(mockDto(i))
        }
        return persons
    }

    fun mockEntity(number: Int = 0): Person {
        val person = Person()
        person.address = "Address Test$number"
        person.firstName = "First Name Test$number"
        person.gender = if (number % 2 == 0) "Male" else "Female"
        person.id = number.toLong()
        person.lastName = "Last Name Test$number"
        return person
    }

    fun mockDto(number: Int = 0): PersonDto {
        val person = PersonDto()
        person.address = "Address Test$number"
        person.firstName = "First Name Test$number"
        person.gender = if (number % 2 == 0) "Male" else "Female"
        person.id = number.toLong()
        person.lastName = "Last Name Test$number"
        return person
    }
}