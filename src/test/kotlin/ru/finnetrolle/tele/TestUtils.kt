package ru.finnetrolle.tele

import org.mockito.Mockito

/**
 * the Hive
 * Licence: MIT
 * Author: Finne Trolle
 */
object Mo {
    fun <T> whenever(call: T) = Mockito.`when`(call)

    fun <T> anyObject(): T {
        return Mockito.anyObject<T>()
    }
}