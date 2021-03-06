package ru.finnetrolle.tele.processing

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.api.objects.User
import ru.finnetrolle.tele.model.Pilot
import ru.finnetrolle.tele.rabbit.ToSend
import ru.finnetrolle.tele.service.external.ExternalRegistrationService
import ru.finnetrolle.tele.service.internal.UserService
import ru.finnetrolle.tele.util.MessageBuilder
import ru.finnetrolle.tele.util.MessageLocalization

/**
 * Telegram bot
 * Licence: MIT
 * Author: Finne Trolle
 */

@Component
open class AuthPreprocessor {

    @Autowired private lateinit var externalRegistrationService: ExternalRegistrationService
    @Autowired private lateinit var userService: UserService
    @Autowired private lateinit var loc: MessageLocalization

    private val KEY_LENGTH: Int = 6

    private fun defineAnswerText(tryApp: ExternalRegistrationService.ApproveResult) = when (tryApp) {
        is ExternalRegistrationService.ApproveResult.Success ->
            loc.getMessage("telebot.fastreg.welcome", tryApp.name)
        is ExternalRegistrationService.ApproveResult.Forbidden ->
            loc.getMessage("telebot.fastreg.forbidden", tryApp.name)
        is ExternalRegistrationService.ApproveResult.TimedOut ->
            loc.getMessage("telebot.fastreg.expired")
        else ->
            loc.getMessage("messages.please.register")
    }

    interface Auth {
        data class Intercepted(val response: ToSend) : Auth
        data class Authorized(val pilot: Pilot, val command: String, val data: String) : Auth
    }

    open fun selectResponse(text: String, user: User, chatId: String): Auth {
        val pilot = userService.getPilot(user.id)
        if (pilot == null) {
            if (text.length == KEY_LENGTH) {
                val regResult = defineAnswerText(externalRegistrationService.tryToApproveContender(text.toUpperCase(), user))
                return Auth.Intercepted(MessageBuilder.build(chatId, regResult))
            } else {
                return Auth.Intercepted(MessageBuilder.build(chatId, loc.getMessage("messages.please.register")))
            }
        } else {
            if (pilot.renegade) {
                return Auth.Intercepted(MessageBuilder.build(chatId, loc.getMessage("messages.renegade")))
            } else {
                return Auth.Authorized(pilot, text.substringBefore(" "), text.substringAfter(" "))
            }
        }
    }

}