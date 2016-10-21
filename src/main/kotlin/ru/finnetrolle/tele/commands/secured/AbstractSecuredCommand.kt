package ru.finnetrolle.tele.service.processing.commands.secured

import ru.finnetrolle.tele.processing.CommandExecutor

/**
 * Telegram bot
 * Licence: MIT
 * Author: Finne Trolle
 */
abstract class AbstractSecuredCommand: CommandExecutor {
    override fun secured() = true
}