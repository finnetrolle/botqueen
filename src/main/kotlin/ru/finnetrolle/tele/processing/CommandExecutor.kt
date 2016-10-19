package ru.finnetrolle.tele.processing

import ru.finnetrolle.tele.model.Pilot

/**
 * the Hive
 * Licence: MIT
 * Author: Finne Trolle
 */

interface CommandExecutor {

    /**
     * name of command - case will be same in description
     */
    fun name(): String

    /**
     * is command only for moderator?
     */
    fun secured(): Boolean

    /**
     * command description for
     */
    fun description(): String

    /**
     * Main method of command - execution
     */
    fun execute(pilot: Pilot, data: String): String

}