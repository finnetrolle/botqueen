package ru.finnetrolle.tele.commands.unsecured

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.finnetrolle.tele.model.Pilot
import ru.finnetrolle.tele.service.external.MailbotService
import ru.finnetrolle.tele.util.MessageLocalization

/**
 * Telegram bot
 * Licence: MIT
 * Author: Finne Trolle
 */

@Component
class MailCommand : AbstractUnsecuredCommand() {

    @Autowired
    private lateinit var loc: MessageLocalization

    @Autowired
    private lateinit var mailbotService: MailbotService

    override fun name() = "/MAIL"

    override fun description() = loc.getMessage("telebot.command.description.mail")

    override fun execute(pilot: Pilot, data: String): String {
        return mailbotService.getLast()
                .map { m -> "*${m.title}*\n*from: ${m.sender}*\n${m.body}\n----------" }
                .joinToString("\n\n")
    }
}