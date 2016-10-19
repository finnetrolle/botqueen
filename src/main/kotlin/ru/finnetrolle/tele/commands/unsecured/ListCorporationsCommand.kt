package ru.finnetrolle.tele.commands.unsecured

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.finnetrolle.tele.model.Pilot
import ru.finnetrolle.tele.util.MessageLocalization

/**
 * Telegram bot
 * Licence: MIT
 * Author: Finne Trolle
 */

@Component
class ListCorporationsCommand : AbstractUnsecuredCommand() {

    @Autowired
    private lateinit var loc: MessageLocalization

//    @Autowired
//    private lateinit var corpService: CorpService

    override fun name() = "/LC"

    override fun description() = loc.getMessage("telebot.command.description.lc")

    override fun execute(pilot: Pilot, data: String): String {
//        val corps = corpService.getAll()
//                .map { a -> "[${a.ticker}] - ${a.title}" }
//                .sorted()
//        return loc.getMessage("messages.response.lc", corps.size, corps.joinToString(separator = "\n"))
        return loc.getMessage("messages.response.lc", 10, "vava\njaja\ndata=$data")
    }
}