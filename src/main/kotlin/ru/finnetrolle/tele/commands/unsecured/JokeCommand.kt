package ru.finnetrolle.tele.commands.unsecured

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.finnetrolle.tele.model.Pilot
import ru.finnetrolle.tele.service.additional.JokeService
import ru.finnetrolle.tele.util.MessageLocalization

/**
 * Telegram bot
 * Licence: MIT
 * Author: Finne Trolle
 */

@Component
class JokeCommand : AbstractUnsecuredCommand() {

    @Autowired
    private lateinit var loc: MessageLocalization

    @Autowired
    private lateinit var joker: JokeService

    override fun name() = "/JOKE"

    override fun description() = loc.getMessage("telebot.command.description.joke")

    override fun execute(pilot: Pilot, data: String) = joker.joke()

}