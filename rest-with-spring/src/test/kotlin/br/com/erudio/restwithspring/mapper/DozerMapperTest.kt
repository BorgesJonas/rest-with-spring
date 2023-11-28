package br.com.erudio.restwithspring.mapper

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import br.com.erudio.restwithspring.data.dto.v1.PersonDto
import br.com.erudio.restwithspring.entities.Person
import br.com.erudio.restwithspring.mapper.mocks.MockPerson

class DozerMapperTest {

    private var inputObject: MockPerson? = null

    @BeforeEach
    fun setUp() {
        inputObject = MockPerson()
    }

    @Test
    fun parseEntityToVOTest() {
        val output: PersonDto = DozerMapper.parseObject(inputObject!!.mockEntity(), PersonDto::class.java)
        assertEquals(0, output.id)
        /**assertEquals("First Name Test0", output.firstName)
        assertEquals("Last Name Test0", output.lastName)
        assertEquals("Address Test0", output.address)
        assertEquals("Male", output.gender) */
    }

    @Test
    fun parseEntityListToVOListTest() {
        val outputList: ArrayList<PersonDto> =
            DozerMapper.parseListObjects(inputObject!!.mockEntityList(), PersonDto::class.java)

        val outputZero: PersonDto = outputList[0]

        assertEquals(0, outputZero.id)
        assertEquals("First Name Test0", outputZero.firstName)
        assertEquals("Last Name Test0", outputZero.lastName)
        assertEquals("Address Test0", outputZero.address)
        assertEquals("Male", outputZero.gender)

        val outputSeven: PersonDto = outputList[7]
        assertEquals(7.toLong(), outputSeven.id)
        assertEquals("First Name Test7", outputSeven.firstName)
        assertEquals("Last Name Test7", outputSeven.lastName)
        assertEquals("Address Test7", outputSeven.address)
        assertEquals("Female", outputSeven.gender)

        val outputTwelve: PersonDto = outputList[12]
        assertEquals(12.toLong(), outputTwelve.id)
        assertEquals("First Name Test12", outputTwelve.firstName)
        assertEquals("Last Name Test12", outputTwelve.lastName)
        assertEquals("Address Test12", outputTwelve.address)
        assertEquals("Male", outputTwelve.gender)
    }

    @Test
    fun parseVOToEntityTest() {

        val output: Person = DozerMapper.parseObject(inputObject!!.mockDto(), Person::class.java)

        assertEquals(0, output.id)
        assertEquals("First Name Test0", output.firstName)
        assertEquals("Last Name Test0", output.lastName)
        assertEquals("Address Test0", output.address)
        assertEquals("Male", output.gender)
    }

    @Test
    fun parserVOListToEntityListTest() {

        val outputList: ArrayList<Person> = DozerMapper.parseListObjects(inputObject!!.mockDtoList(), Person::class.java)

        val outputZero: Person = outputList[0]
        assertEquals(0, outputZero.id)
        assertEquals("First Name Test0", outputZero.firstName)
        assertEquals("Last Name Test0", outputZero.lastName)
        assertEquals("Address Test0", outputZero.address)
        assertEquals("Male", outputZero.gender)

        val outputSeven: Person = outputList[7]
        assertEquals(7, outputSeven.id)
        assertEquals("First Name Test7", outputSeven.firstName)
        assertEquals("Last Name Test7", outputSeven.lastName)
        assertEquals("Address Test7", outputSeven.address)
        assertEquals("Female", outputSeven.gender)

        val outputTwelve: Person = outputList[12]
        assertEquals(12, outputTwelve.id)
        assertEquals("First Name Test12", outputTwelve.firstName)
        assertEquals("Last Name Test12", outputTwelve.lastName)
        assertEquals("Address Test12", outputTwelve.address)
        assertEquals("Male", outputTwelve.gender)
    }
}