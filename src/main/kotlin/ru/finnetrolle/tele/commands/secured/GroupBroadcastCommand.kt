package ru.finnetrolle.tele.service.processing.commands.secured

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
open class GroupBroadcastCommand : AbstractSecuredCommand() {

    @Autowired
    private lateinit var userService: UserService

//    @Autowired
//    private lateinit var telegram: TelegramBotService

    @Autowired
    private lateinit var provider: MessageProvider

    @Autowired
    private lateinit var loc: MessageLocalization

    override fun name() = "/GC"

    override fun description() = loc.getMessage("telebot.command.description.gc")

    override fun execute(pilot: Pilot, data: String): String {
        val text = data.substringAfter(" ")
        val groupName = data.substringBefore(" ")
        val users = userService.getLegalUsers(groupName)
        val message = "Broadcast from ${pilot.characterName} to $groupName at ${Date()} \n$text"
        users.forEach { u -> provider.publish(MessageBuilder.build(u.id.toString(), message)) }
        return loc.getMessage("messages.broadcast.result", users.size)
    }
}