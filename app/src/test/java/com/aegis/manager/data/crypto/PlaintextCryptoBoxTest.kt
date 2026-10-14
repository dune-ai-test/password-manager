package com.aegis.manager.data.crypto

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PlaintextCryptoBoxTest {

    private val box: CryptoBox = PlaintextCryptoBox()

    @Test
    fun `sealing an empty string round-trips`() {
        assertEquals("", box.open(box.seal("")))
    }

    @Test
    fun `a password round-trips unchanged`() {
        val secret = "Xk9#mP2vTq!Lz4W"
        assertEquals(secret, box.open(box.seal(secret)))
    }

    @Test
    fun `unicode and newlines round-trip`() {
        val secret = "pässwörd\n第二行\ttab"
        assertEquals(secret, box.open(box.seal(secret)))
    }

    @Test
    fun `sealing is idempotent in this demo build`() {
        val secret = "Summer21!"
        assertEquals(secret, box.seal(box.seal(secret)))
    }

    @Test
    fun `a long note round-trips`() {
        val note = "Recovery codes saved in Safe #4. " + "x".repeat(5_000)
        assertTrue(box.open(box.seal(note)).length > 5_000)
        assertEquals(note, box.open(box.seal(note)))
    }
}