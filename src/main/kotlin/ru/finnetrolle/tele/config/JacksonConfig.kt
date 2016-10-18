package ru.finnetrolle.tele.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import com.fasterxml.jackson.module.kotlin.KotlinModule

@Configuration
open class JacksonConfig {
    @Bean open fun kotlinModule() = KotlinModule()
}