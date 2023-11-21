package br.com.erudio.restwithspring.data.dto.v1

data class PersonDto(
    var id: Long = 0,

    var firstName: String = "",

    var lastName: String = "",

    var address: String = "",

    var gender: String = ""
)