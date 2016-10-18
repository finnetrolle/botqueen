package ru.finnetrolle.tele.restful

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.finnetrolle.tele.rabbit.MessageProvider

@RestController
@RequestMapping()
class TestController {

    @Autowired
    private lateinit var provider: MessageProvider

    @GetMapping("/test")
    fun init() = ResponseEntity.ok("OK")

}