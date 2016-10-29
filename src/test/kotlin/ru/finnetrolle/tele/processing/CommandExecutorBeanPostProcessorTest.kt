package ru.finnetrolle.tele.processing

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import ru.finnetrolle.tele.Mo
import ru.finnetrolle.tele.Mo.anyObject
import ru.finnetrolle.tele.model.Pilot
import ru.finnetrolle.tele.service.processing.commands.secured.AbstractSecuredCommand
import ru.finnetrolle.tele.service.processing.commands.secured.CheckCommand
import ru.finnetrolle.tele.util.MessageLocalization

/**
 * the Hive
 * Licence: MIT
 * Author: Finne Trolle
 */
class CommandExecutorBeanPostProcessorTest {

    @Mock
    private lateinit var ces: CommandExecutorService


    @InjectMocks
    private var processor = CommandExecutorBeanPostProcessor()

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testFineClass() {
        Mockito.doNothing().`when`(ces).addExecutor(anyObject())
        val command = object : AbstractSecuredCommand() {
            override fun name() = "name"
            override fun description() = "desc"
            override fun execute(pilot: Pilot, data: String) = "wow"
        }
        processor.postProcessBeforeInitialization(command, CheckCommand::class.java.name)
        Mockito.verify(ces, Mockito.times(1)).addExecutor(anyObject())
    }

    @Test
    fun testBadClass() {
        Mockito.doNothing().`when`(ces).addExecutor(anyObject())
        processor.postProcessBeforeInitialization(Object(), Object::class.java.name)
        Mockito.verify(ces, Mockito.times(0)).addExecutor(anyObject())
    }

    @Test
    fun afterWorksFine() {
        val o = Pilot()
        assertEquals(o, processor.postProcessAfterInitialization(o, "name"))
    }

}