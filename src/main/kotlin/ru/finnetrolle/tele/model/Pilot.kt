package ru.finnetrolle.tele.model

/**
 * the Hive
 * Licence: MIT
 * Author: Finne Trolle
 */

//@Entity
//@Table(name = "pilots")
data class Pilot (
//        @Id
        var id: Int = 0, // telegram id is PK
        var firstName: String? = "",
        var lastName: String? = "",
        var username: String? = "",
        var characterName: String = "",
        var characterId: Long = 0,
        var moderator: Boolean = false,
        var renegade: Boolean = false
)