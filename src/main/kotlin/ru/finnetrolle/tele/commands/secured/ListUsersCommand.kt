package ru.finnetrolle.tele.service.processing.commands.secured

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.finnetrolle.tele.model.Pilot
import ru.finnetrolle.tele.service.internal.UserService
import ru.finnetrolle.tele.util.MessageLocalization

/**
 * Telegram bot
 * Licence: MIT
 * Author: Finne Trolle
 */

@Component
class ListUsersCommand : AbstractSecuredCommand() {

    @Autowired
    private lateinit var loc: MessageLocalization

    @Autowired
    private lateinit var userService: UserService

    override fun name() = "/LU"

    override fun description() = loc.getMessage("telebot.command.description.lu")

    override fun execute(pilot: Pilot, data: String): String {
        val users = userService.getAllUsers()
                .map { u -> "${u.characterName}${renemark(u)}${modermark(u)}" }
                .sorted()
        return loc.getMessage("messages.response.lu", users.size, users.joinToString(separator = "\n"))
    }

    private fun renemark(pilot: Pilot) = if (pilot.renegade) " >> Renegade" else ""
    private fun modermark(pilot: Pilot) = if (pilot.moderator) " >> Moderator" else ""
}