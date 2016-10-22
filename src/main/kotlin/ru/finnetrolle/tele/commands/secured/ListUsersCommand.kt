package ru.finnetrolle.tele.service.processing.commands.secured

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.finnetrolle.tele.model.Pilot
import ru.finnetrolle.tele.service.internal.UserService
import ru.finnetrolle.tele.util.MessageLocalization
import ru.finnetrolle.tele.util.Utilz

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
        val groupUsers = userService.getLegalUsers(data)
        if (groupUsers.isNotEmpty()) {
            return loc.getMessage("cmd.lu.group", data, groupUsers.size, Utilz.formatList(groupUsers))
        }

        return when (data.toUpperCase()) {
            "R" -> {
                val users = userService.getRenegades()
                loc.getMessage("cmd.lu.r", users.size, Utilz.formatList(users))
            }
            "M" -> {
                val users = userService.getModerators()
                loc.getMessage("cmd.lu.m", users.size, Utilz.formatList(users))
            }
            else -> {
                val users = userService.getLegalUsers()
                loc.getMessage("cmd.lu.all", users.size, Utilz.formatList(users))
            }
        }
    }

}