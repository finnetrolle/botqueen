package ru.finnetrolle.tele.util

import org.slf4j.LoggerFactory
import org.springframework.context.MessageSource
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct

/**
 * the Hive
 * Licence: MIT
 * Author: Finne Trolle
 */
@Component
open class MessageLocalization {

    private var messageSource: MessageSource? = null

    constructor() {
        this.messageSource = getMessageSource()
    }

    constructor(messageSource: ReloadableResourceBundleMessageSource) {
        this.messageSource = messageSource
    }

    private fun getMessageSource(): MessageSource {
        val messageSource = ReloadableResourceBundleMessageSource()
        messageSource.setBasename("messages")
        messageSource.setDefaultEncoding("UTF-8")
        return messageSource
    }

    @PostConstruct
    fun init() {
        log.info(getMessage("message.system.load.approve"))
    }

    open fun getMessage(message: String): String {
        return this.messageSource!!.getMessage(message, null, Locale.getDefault())
    }

    open fun getMessage(message: String, vararg params: Any): String {
        return messageSource!!.getMessage(message, params, Locale.getDefault())
    }

    companion object {
        private val log = LoggerFactory.getLogger(MessageLocalization::class.java)
    }

}