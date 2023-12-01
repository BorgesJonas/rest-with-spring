package br.com.erudio.restwithspring.integrationtests.dto

data class PersonDto(
    var id: Long = 0,
    var firstName: String = "",
    var lastName: String = "",
    var address: String = "",
    var gender: String = ""
)