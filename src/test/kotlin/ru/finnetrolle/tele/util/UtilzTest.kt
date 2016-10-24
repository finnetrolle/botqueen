package ru.finnetrolle.tele.util

import org.junit.Assert.*
import org.junit.Test
import ru.finnetrolle.tele.model.Pilot

/**
 * the Hive
 * Licence: MIT
 * Author: Finne Trolle
 */
class UtilzTest {

    @Test
    fun sortIsCaseInsensetive() {
        val list = listOf(
                "Abdulla",
                "Dimirhan",
                "dimirhancheg",
                "abdullah")
                .map { s -> Pilot(characterName = s) }
        val result = listOf(
                "Abdulla",
                "abdullah",
                "Dimirhan",
                "dimirhancheg")
                .map { s -> Pilot(characterName = s) }
        val sb = StringBuilder()
            .append("Abdulla").append("\n")
            .append("abdullah").append("\n")
            .append("Dimirhan").append("\n")
            .append("dimirhancheg")
        assertEquals(sb.toString(), Utilz.formatList(list))
    }

}