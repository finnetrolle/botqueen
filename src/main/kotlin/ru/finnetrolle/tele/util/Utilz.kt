package ru.finnetrolle.tele.util

import ru.finnetrolle.tele.model.Pilot

/**
 * the Hive
 * Licence: MIT
 * Author: Finne Trolle
 */
object Utilz {

    fun formatList(users: Collection<Pilot>) = users
            .map { u -> u.characterName}
            .sortedBy(String::toUpperCase)
            .joinToString("\n")

}