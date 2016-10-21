package ru.finnetrolle.tele.rabbit

import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.api.objects.Message
import ru.finnetrolle.tele.model.Pilot
import ru.finnetrolle.tele.processing.AuthPreprocessor
import ru.finnetrolle.tele.processing.CommandExecutorService
import ru.finnetrolle.tele.util.MessageBuilder

/**
 * Created by maxsyachin on 18.10.16.
 */

@Component
open class MessageListener {

    private val LOG = LoggerFactory.getLogger(MessageListener::class.java)

    @Autowired
    private lateinit var provider: MessageProvider

    @Autowired
    private lateinit var executor: CommandExecutorService

    @Autowired
    private lateinit var auth: AuthPreprocessor

    @RabbitListener(queues = arrayOf("\${rabbit.received.q}"))
    open fun processCallback(message: Message) {
        LOG.debug("{PROCESS_MESSAGE} ${message.chatId}")
        try {
            val authResult = auth.selectResponse(message.text, message.from, message.chatId.toString())
            LOG.debug("Auth result is $authResult")
            val response = when (authResult) {
                is AuthPreprocessor.Auth.Intercepted -> {
                    LOG.debug("intercepted")
                    authResult.response
                }
                is AuthPreprocessor.Auth.Authorized -> {
                    LOG.debug("authorized")
                    executor.execute(
                            message.text.substringBefore(" "),
                            message.text.substringAfter(" "),
                            Pilot(),
                            message.chatId.toString())
                }
                else -> {
                    LOG.error("Auth failed and something is very bad")
                    MessageBuilder.build(message.chatId.toString(), "This option is impossible")
                }
            }
            LOG.debug("response is $response")
            provider.processMessage(response)
        } catch (e: Exception) {
            LOG.error("Exception found", e)
        }
    }

}