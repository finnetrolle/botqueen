package ru.finnetrolle.tele.rabbit

import org.slf4j.LoggerFactory
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.api.methods.send.SendMessage
import ru.finnetrolle.tele.util.MessageSplitter
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

    open fun processMessage(message: ToSend)
    {
        MessageSplitter.splitLargeMessages(message)
                .forEach { m -> template.convertAndSend(exchangeName, "", m) }
    }

    open fun publish(message: ToSend) = MessageSplitter.splitLargeMessages(message)
            .map { m ->
                template.convertAndSend(exchangeName, "", m)
                1
            }.sum()

}