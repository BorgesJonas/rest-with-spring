package br.com.erudio.restwithspring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RestWithSpringApplication

fun main(args: Array<String>) {
	runApplication<RestWithSpringApplication>(*args)
}
