package com.kadaster.bag_api

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import java.io.File
import java.util.Properties

@SpringBootApplication
@EnableConfigurationProperties
class BagApiApplication

fun main(args: Array<String>) {
    val app = SpringApplication(BagApiApplication::class.java)

    val props = Properties()
    val envFile = File(".env.properties")
    if (envFile.exists()) {
        envFile.inputStream().use { props.load(it) }
    }

    app.setDefaultProperties(props)
    app.run(*args)
}
