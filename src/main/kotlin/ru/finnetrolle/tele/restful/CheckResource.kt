package ru.finnetrolle.tele.restful

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.finnetrolle.tele.service.internal.UserService
import ru.finnetrolle.tele.util.MessageLocalization


/**
 * the Hive
 * Licence: MIT
 * Author: Finne Trolle
 */
@RestController
@RequestMapping("/check")
class CheckResource {

    @Autowired private lateinit var userService: UserService

    @Autowired private lateinit var loc: MessageLocalization

    private val LOG = LoggerFactory.getLogger(CheckResource::class.java)

    data class CheckResult(val checked: Int, val badGuys: List<String>, val msec: Long)
    data class CheckRequest(var secret: String)

    @Value("\${api.secret.check}")
    private lateinit var secret: String

    @PostMapping
    @ResponseBody
    fun check(@RequestBody body: CheckRequest): ResponseEntity<CheckResult> {
        if (body.secret == secret) {
            val start = System.currentTimeMillis()
            val result = userService.check()
            return ResponseEntity.ok(CheckResult(result.checked, result.renegaded, System.currentTimeMillis() - start))
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null)
    }

}