package ru.finnetrolle.tele.util

import org.junit.Assert.*
import org.junit.Test
import ru.finnetrolle.tele.rabbit.ToSend

/**
 * the Hive
 * Licence: MIT
 * Author: Finne Trolle
 */
class MessageSplitterTest {

    @Test
    fun testNoSplit() {
        val sb = StringBuilder()
        (0..500).forEach { i -> sb.append("word ") }
        val message = MessageBuilder.build("someId", sb.toString())
        val messages = MessageSplitter.splitLargeMessages(message)
        assertEquals(1, messages.size)
    }

    @Test
    fun testSplit() {
        val sb = StringBuilder()
        (0..1500).forEach { i -> sb.append("word ") }
        val message = MessageBuilder.build("someId", sb.toString())
        val messages = MessageSplitter.splitLargeMessages(message)
        assertEquals(2, messages.size)
    }

}