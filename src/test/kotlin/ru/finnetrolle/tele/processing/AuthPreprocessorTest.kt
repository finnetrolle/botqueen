package ru.finnetrolle.tele.processing

import org.json.JSONObject
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.telegram.telegrambots.api.objects.User
import ru.finnetrolle.tele.Mo.anyObject
import ru.finnetrolle.tele.Mo.whenever
import ru.finnetrolle.tele.model.Pilot
import ru.finnetrolle.tele.rabbit.ToSend
import ru.finnetrolle.tele.service.external.ExternalRegistrationService
import ru.finnetrolle.tele.service.internal.UserService
import ru.finnetrolle.tele.util.MessageBuilder
import ru.finnetrolle.tele.util.MessageLocalization

/**
 * the Hive
 * Licence: MIT
 * Author: Finne Trolle
 */
class AuthPreprocessorTest {

    @Mock private lateinit var externalRegistrationService: ExternalRegistrationService
    @Mock private lateinit var userService: UserService
    @Mock private lateinit var loc: MessageLocalization
    @Mock private lateinit var USER: User

    @InjectMocks
    private val auth = AuthPreprocessor()

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        whenever(loc.getMessage(Mockito.anyString()))
                .thenAnswer { u -> u.arguments[0] }
        whenever(loc.getMessage(Mockito.anyString(), anyObject()))
                .thenAnswer { u -> u.arguments[0] }
        whenever(USER.id).thenReturn(USER_ID)
    }

    private val CHAT_ID: String = "12345"
    private val RE_RENEGADE: ToSend = MessageBuilder.build(CHAT_ID, "messages.renegade")
    private val RE_FORBIDDEN: ToSend = MessageBuilder.build(CHAT_ID, "telebot.fastreg.forbidden")
    private val RE_REGISTER_PLEASE = MessageBuilder.build(CHAT_ID, "messages.please.register")
    private val RE_ALLOWED = MessageBuilder.build(CHAT_ID, "telebot.fastreg.welcome")
    private val RE_EXPIRED = MessageBuilder.build(CHAT_ID, "telebot.fastreg.expired")
    private val TEXT: String = "Some text"
    private val USER_ID: Int = 10

    @Test
    fun renegadeShallNotPass() {
        whenever(userService.getPilot(USER_ID)).thenReturn(Pilot(renegade = true))
        assertEquals(
                AuthPreprocessor.Auth.Intercepted(RE_RENEGADE),
                auth.selectResponse(TEXT, USER, CHAT_ID))
    }

    private val FAIL_KEY: String = "123456"
    private val COMMAND: String = "do"
    private val DATA: String = "some"



    @Test
    fun unknownPilotWithoutKeyInMessageGoesAway() {
        whenever(userService.getPilot(USER_ID)).thenReturn(null)
        assertEquals(
                AuthPreprocessor.Auth.Intercepted(RE_REGISTER_PLEASE),
                auth.selectResponse("1234567", USER, CHAT_ID))
        whenever(externalRegistrationService.tryToApproveContender(anyObject(), anyObject()))
                .thenReturn(null)
        assertEquals(
                AuthPreprocessor.Auth.Intercepted(RE_REGISTER_PLEASE),
                auth.selectResponse(FAIL_KEY, USER, CHAT_ID))
    }

    @Test
    fun forbiddenPilotsNotAllowed() {
        whenever(userService.getPilot(USER_ID)).thenReturn(null)
        whenever(externalRegistrationService.tryToApproveContender(anyObject(), anyObject()))
                .thenReturn(ExternalRegistrationService.ApproveResult.Forbidden("name"))
        assertEquals(
                AuthPreprocessor.Auth.Intercepted(RE_FORBIDDEN),
                auth.selectResponse(FAIL_KEY, USER, CHAT_ID))
    }

    @Test
    fun externalExpiratorWorksFine() {
        whenever(userService.getPilot(USER_ID)).thenReturn(null)
        whenever(externalRegistrationService.tryToApproveContender(anyObject(), anyObject()))
                .thenReturn(ExternalRegistrationService.ApproveResult.Success("name", "corp", "ally"))
        assertEquals(
                AuthPreprocessor.Auth.Intercepted(RE_ALLOWED),
                auth.selectResponse(FAIL_KEY, USER, CHAT_ID))
    }

    @Test
    fun expiredExternalSystemProvidesExpiredString() {
        whenever(userService.getPilot(USER_ID)).thenReturn(null)
        whenever(externalRegistrationService.tryToApproveContender(anyObject(), anyObject()))
                .thenReturn(ExternalRegistrationService.ApproveResult.TimedOut(10))
        assertEquals(
                AuthPreprocessor.Auth.Intercepted(RE_EXPIRED),
                auth.selectResponse(FAIL_KEY, USER, CHAT_ID))
    }

    @Test
    fun succesAuthProvidesAuthInfo() {
        whenever(userService.getPilot(USER_ID)).thenReturn(Pilot(renegade = false))
        assertEquals(
               AuthPreprocessor.Auth.Authorized(Pilot(), COMMAND, DATA),
               auth.selectResponse(COMMAND + " " + DATA, USER, CHAT_ID))
    }

}