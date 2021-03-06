package ru.finnetrolle.tele.service.processing.commands.secured

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.finnetrolle.tele.model.Pilot
import ru.finnetrolle.tele.service.internal.AllyService
import ru.finnetrolle.tele.util.MessageLocalization

/**
 * Telegram bot
 * Licence: MIT
 * Author: Finne Trolle
 */

@Component
class RemoveAllianceCommand: AbstractSecuredCommand() {

    @Autowired
    private lateinit var allyService: AllyService

    @Autowired
    private lateinit var loc: MessageLocalization

    override fun name() = "/RMALLY"

    override fun description() = loc.getMessage("telebot.command.description.rmally")

    override fun execute(pilot: Pilot, data: String): String {
        val result = allyService.removeAlly(data)
        return when (result) {
            is AllyService.RemoveResponse.AllianceRemoved -> loc.getMessage("messages.ally.removed")
            is AllyService.RemoveResponse.AllianceNotFound -> loc.getMessage("messages.ally.not.found")
            else -> loc.getMessage("messages.impossible")
        }
    }
}