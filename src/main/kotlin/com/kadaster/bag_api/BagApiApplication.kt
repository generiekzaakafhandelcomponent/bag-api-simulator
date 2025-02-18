package com.kadaster.bag_api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BagApiApplication

fun main(args: Array<String>) {
	runApplication<BagApiApplication>(*args)
}
