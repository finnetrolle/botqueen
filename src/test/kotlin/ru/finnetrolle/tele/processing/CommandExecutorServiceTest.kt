package ru.finnetrolle.tele.processing

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import ru.finnetrolle.tele.Mo
import ru.finnetrolle.tele.model.Pilot
import ru.finnetrolle.tele.util.MessageBuilder
import ru.finnetrolle.tele.util.MessageLocalization

/**
 * the Hive
 * Licence: MIT
 * Author: Finne Trolle
 */
class CommandExecutorServiceTest {

    @Mock
    private lateinit var loc: MessageLocalization

    @InjectMocks
    private val service = CommandExecutorService()

    private val secured: CommandExecutor = object : CommandExecutor {
        override fun name() = "name"
        override fun secured() = true
        override fun description() = "desc"
        override fun execute(pilot: Pilot, data: String) = "executed"
    }

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        Mo.whenever(loc.getMessage(Mockito.anyString()))
                .thenAnswer { u -> u.arguments[0] }
        Mo.whenever(loc.getMessage(Mockito.anyString(), Mo.anyObject()))
                .thenAnswer { u -> u.arguments[0] }
    }

    private val CHAT_ID: String = "12345"

    @Test
    fun executorCanBeAdded() {
        service.addExecutor(secured)
        assertEquals(
                MessageBuilder.build(CHAT_ID, "executed"),
                service.execute("name", "data", Pilot(moderator = true), CHAT_ID))
    }

}