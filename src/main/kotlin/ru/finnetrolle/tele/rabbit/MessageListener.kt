package ru.finnetrolle.tele.rabbit

import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.api.objects.Message
import ru.finnetrolle.tele.util.MessageBuilder

/**
 * Created by maxsyachin on 18.10.16.
 */

@Component
open class MessageListener {

    private val LOG = LoggerFactory.getLogger(MessageListener::class.java)

    @Autowired
    private lateinit var provider: MessageProvider

    @RabbitListener(queues = arrayOf("\${rabbit.received.q}"))
    open fun processCallback(message: Message) {
        LOG.debug("{PROCESS_MESSAGE} ${message.chatId}")
        val ans = ToSend()

        ans.method = "sendmessage"
        ans.chatId = message.chatId.toString()
        ans.disableNotification = false
        ans.disableWebPagePreview = false
        ans.parseMode = ""
        ans.replyToMessageId = message.messageId
        ans.text = message.text

        provider.processMessage(ans)
    }

}