package br.com.erudio.restwithspring.exceptions

import java.util.Date

data class ExceptionResponse (
    val timestamp: Date,
    val message: String?,
    val details: String,
)