package ru.finnetrolle.tele.restful

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import ru.finnetrolle.tele.model.Mail
import ru.finnetrolle.tele.service.external.MailbotService

/**
 * Licence: MIT
 * Legion of xXDEATHXx notification bot for telegram
 * Created by maxsyachin on 12.04.16.
 */

@Controller
@RequestMapping("/test")
class MailResource {

    @Autowired lateinit private var mailbot: MailbotService

    @RequestMapping(method = arrayOf(RequestMethod.GET))
    @ResponseBody
    fun mail(): ResponseEntity<List<Mail>> {
        mailbot.receiveMail()
        return ResponseEntity.ok(mailbot.getLast())
    }

}