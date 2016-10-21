package ru.finnetrolle.tele.service.processing.commands.secured

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.finnetrolle.tele.model.Pilot
import ru.finnetrolle.tele.rabbit.MessageProvider
import ru.finnetrolle.tele.service.internal.UserService
import ru.finnetrolle.tele.util.MessageBuilder
import ru.finnetrolle.tele.util.MessageLocalization
import java.util.*

/**
 * Telegram bot
 * Licence: MIT
 * Author: Finne Trolle
 */
@Component
open class GlobalBroadcasterCommand : AbstractSecuredCommand() {

    @Autowired
    private lateinit var userService: UserService

//    @Autowired
//    private lateinit var telegram: TelegramBotService

    @Autowired
    private lateinit var provider: MessageProvider

    @Autowired
    private lateinit var loc: MessageLocalization

    private val log = LoggerFactory.getLogger(GlobalBroadcasterCommand::class.java)

    override fun name() = "/CAST"

    override fun description() = loc.getMessage("telebot.command.description.cast")

    override fun execute(pilot: Pilot, data: String): String {
        try {
            val users = userService.getLegalUsers()
            val message = "Broadcast from ${pilot.characterName} at ${Date()} \n$data"
            users.forEach { u -> provider.publish(MessageBuilder.build(u.id.toString(), message)) }
            return loc.getMessage("messages.broadcast.result", users.size)
        } catch (e: Exception) {
            log.error("Can't execute command global broadcast because of", e)
        }
        return "Some very bad happened"
    }
}