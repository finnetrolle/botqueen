package ru.finnetrolle.tele.rabbit

import org.springframework.amqp.core.AmqpTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.api.methods.send.SendMessage

/**
 * Created by maxsyachin on 18.10.16.
 */

@Component
open class MessageProvider {

    @Autowired
    private lateinit var template: AmqpTemplate

    @Value("\${rabbit.tosend.e}")
    private lateinit var exchangeName: String

    open fun processMessage(message: SendMessage) {
        template.convertAndSend(exchangeName, "", message)
    }

}