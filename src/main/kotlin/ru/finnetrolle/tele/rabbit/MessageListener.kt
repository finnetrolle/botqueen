package ru.finnetrolle.tele.rabbit

import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.api.objects.Message
import ru.finnetrolle.tele.model.Pilot
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

    @RabbitListener(queues = arrayOf("\${rabbit.received.q}"))
    open fun processCallback(message: Message) {
        LOG.debug("{PROCESS_MESSAGE} ${message.chatId}")

        val result = executor.execute(
                message.text.substringBefore(" "),
                message.text.substringAfter(" "),
                Pilot(),
                message.chatId.toString());

        /*
        return Auth.Authorized(pilot, text.substringBefore(" "), text.substringAfter(" "))

        open fun process(command: String, data: String, pilot: Pilot): SendMessage {
        log.debug("Processing command $command from $pilot with data = $data")
        return ces.execute(command, data, pilot, pilot.id.toString())
    }
         */

        provider.processMessage(result)
    }

}