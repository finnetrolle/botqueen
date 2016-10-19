package ru.finnetrolle.tele.util

import ru.finnetrolle.tele.rabbit.ToSend

/**
 * the Hive
 * Licence: MIT
 * Author: Finne Trolle
 */
object MessageBuilder {

    fun build(chatId: String, text: String): ToSend {
        return build(chatId, text, 0);
    }

    fun build(chatId: String, text: String, replyTo: Int): ToSend {
        val ans = ToSend()
        ans.method = "sendmessage"
        ans.chatId = chatId
        ans.disableNotification = false
        ans.disableWebPagePreview = false
        ans.parseMode = ""
        ans.replyToMessageId = replyTo
        ans.text = text
        return ans
    }

}