package ru.finnetrolle.tele.rabbit

import org.slf4j.LoggerFactory
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.api.methods.send.SendMessage
import javax.annotation.PostConstruct

/**
 * Created by maxsyachin on 18.10.16.
 */

@Component
open class MessageProvider {

    private val LOG = LoggerFactory.getLogger(MessageProvider::class.java)

    @Autowired
    private lateinit var template: AmqpTemplate

    @Value("\${rabbit.tosend.e}")
    private lateinit var exchangeName: String

    @PostConstruct
    open fun init() {
        LOG.info(" >>>>>>>>>>>>>> <<<<<<<<<<<<<<")
    }

    open fun processMessage(message: ToSend) {
        LOG.info("Message push ${message.text}")
        template.convertAndSend(exchangeName, "", message)
    }

}