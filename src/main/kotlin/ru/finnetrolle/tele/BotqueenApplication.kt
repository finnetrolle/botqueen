package ru.finnetrolle.tele

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication

open class BotqueenApplication

fun main(args: Array<String>) {
    SpringApplication.run(BotqueenApplication::class.java, *args)
}
