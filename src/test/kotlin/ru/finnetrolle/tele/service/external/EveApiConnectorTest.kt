package ru.finnetrolle.tele.service.external

import org.junit.Assert.*
import org.junit.Test

/**
 * the Hive
 * Licence: MIT
 * Author: Finne Trolle
 */
class EveApiConnectorTest {


    val eve = EveApiConnector()

    @Test
    fun testGetCharacters() {
        assert(eve.getCharacters(0, "") == null)
    }

    @Test
    fun testGetCorpId() {
        assert(eve.getCorpId(0) == 0L)
    }

    @Test
    fun testIsAllianceExist() {
        assert(eve.isAllianceExist("X.I.X") == true)
        assert(eve.isAllianceExist("NONONO")== false)
    }

    @Test
    fun testGetAlliance() {
        assert(eve.getAlliance("X.I.X")!!.name.equals("Legion of xXDEATHXx"))
        assert(eve.getAlliance("NONONO")==null)
    }

    @Test
    fun testGetCorporation() {
        assert(eve.getCorporation(877122797)!!.ticker.equals("XDSQX"))
        assert(eve.getCorporation(-1) == null)
    }
}