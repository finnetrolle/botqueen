package ru.finnetrolle.tele.commands.unsecured

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
class ListModeratorsCommand: AbstractUnsecuredCommand() {

    @Autowired
    private lateinit var loc: MessageLocalization

    @Autowired
    private lateinit var userService: UserService

    override fun name() = "/LM"

    override fun description() = loc.getMessage("telebot.command.description.lm")

    override fun execute(pilot: Pilot, data: String): String {
        val moders = userService.getModerators()
        return loc.getMessage("messages.response.lm", moders.size, Utilz.formatList(moders))
    }
}