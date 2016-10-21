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
class LegalizeCommand : AbstractSecuredCommand() {

    @Autowired
    private lateinit var loc: MessageLocalization

    @Autowired
    private lateinit var userService: UserService

    override fun name() = "/LEGALIZE"

    override fun description() = loc.getMessage("telebot.command.description.legalize")

    override fun execute(pilot: Pilot, data: String): String {
        val result = userService.setRenegade(data, false)
        if (result == null) {
            return loc.getMessage("messages.user.not.found")
        } else {
            return loc.getMessage("messages.user.legalized", result.characterName)
        }
    }
}