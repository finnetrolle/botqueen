package ru.finnetrolle.tele.commands.unsecured

import ru.finnetrolle.tele.processing.CommandExecutor


/**
 * Telegram bot
 * Licence: MIT
 * Author: Finne Trolle
 */
abstract class AbstractUnsecuredCommand: CommandExecutor {
    override fun secured() = false
}