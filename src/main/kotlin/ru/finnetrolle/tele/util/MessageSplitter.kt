package ru.finnetrolle.tele.util

import ru.finnetrolle.tele.rabbit.ToSend

/**
 * the Hive
 * Licence: MIT
 * Author: Finne Trolle
 */

object MessageSplitter {

    fun splitLargeMessages(message: ToSend): Collection<ToSend> {
        val msgs = mutableListOf<ToSend>()
        var sb = StringBuilder()
        message.text.split(" ").forEach { w ->
            if (sb.length + w.length > 4000) {
                msgs.add(MessageBuilder.build(message.chatId, sb.toString()))
                sb = StringBuilder()
            }
            sb.append(w)
        }
        return msgs
    }

}