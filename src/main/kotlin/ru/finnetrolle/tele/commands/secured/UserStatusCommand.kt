package ru.finnetrolle.tele.service.processing.commands.secured

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.finnetrolle.tele.model.Pilot
import ru.finnetrolle.tele.model.PilotRepository
import ru.finnetrolle.tele.service.internal.UserService
import ru.finnetrolle.tele.util.MessageLocalization

/**
 * Telegram bot
 * Licence: MIT
 * Author: Finne Trolle
 */
@Component
class UserStatusCommand : AbstractSecuredCommand() {

    @Autowired
    private lateinit var loc: MessageLocalization

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var repo: PilotRepository

    override fun name() = "/US"

    override fun description() = loc.getMessage("telebot.command.description.us")

    override fun execute(pilot: Pilot, data: String): String {
        val user = repo.findByCharacterName(data)
        if (user == null) {
            return loc.getMessage("messages.user.not.found")
        } else {
            val moder = if (user.moderator) " is moderator" else ""
            val renegade = if (user.renegade) " is renegade" else ""
            val additional = if (moder.isEmpty() && renegade.isEmpty()) " просто поц" else "$moder$renegade"
            return "${user.characterName}$additional"
        }
    }
}