package com.aegis.manager.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class StrengthEstimatorTest {

    @Test
    fun `empty password scores zero`() {
        assertEquals(0, StrengthEstimator.score(""))
        assertEquals(Strength.WEAK, StrengthEstimator.estimate(""))
    }

    @Test
    fun `common passwords are weak`() {
        listOf("password", "123456", "qwerty", "letmein", "admin").forEach {
            assertEquals("$it should be weak", Strength.WEAK, StrengthEstimator.estimate(it))
        }
    }

    @Test
    fun `short low variety passwords are weak`() {
        assertEquals(Strength.WEAK, StrengthEstimator.estimate("abc"))
        assertEquals(Strength.WEAK, StrengthEstimator.estimate("aaaa"))
    }

    @Test
    fun `a long mixed password is excellent`() {
        val password = "Xk9#mP2vTq!Lz4W"
        assertEquals(Strength.EXCELLENT, StrengthEstimator.estimate(password))
        assertTrue(StrengthEstimator.score(password) >= 85)
    }

    @Test
    fun `longer passwords score higher than shorter ones`() {
        val short = StrengthEstimator.score("Ab3\$xy")
        val long = StrengthEstimator.score("Ab3\$xyAb3\$xyAb3\$x")
        assertTrue("expected $long > $short", long > short)
    }

    @Test
    fun `score always lands inside zero to one hundred`() {
        val samples = listOf(
            "", "a", "aaaaaaaa", "Zz9\$".repeat(10), "Summer21!", "correct horse battery staple",
            "p@ssw0rd1234", "Tr0ub4dor&3", "aaaaaaaaaaaaaaaaaaaaaaaa",
        )
        samples.forEach {
            val score = StrengthEstimator.score(it)
            assertTrue("$it scored $score", score in 0..100)
        }
    }

    @Test
    fun `buckets map onto the expected score boundaries`() {
        assertEquals(Strength.WEAK, StrengthEstimator.strengthOf(0))
        assertEquals(Strength.WEAK, StrengthEstimator.strengthOf(29))
        assertEquals(Strength.FAIR, StrengthEstimator.strengthOf(30))
        assertEquals(Strength.GOOD, StrengthEstimator.strengthOf(50))
        assertEquals(Strength.STRONG, StrengthEstimator.strengthOf(70))
        assertEquals(Strength.EXCELLENT, StrengthEstimator.strengthOf(85))
        assertEquals(Strength.EXCELLENT, StrengthEstimator.strengthOf(100))
    }

    @Test
    fun `keyboard sequences are penalised`() {
        val plain = "Xk9#mP2vTq"
        val sequenced = "qwertyuiop"
        assertTrue(
            "sequence should score lower",
            StrengthEstimator.score(sequenced) < StrengthEstimator.score(plain),
        )
    }

    @Test
    fun `repeated runs are penalised`() {
        val varied = "Xk9#mP2vTq"
        val repeated = "aaaaaaaaaaaaaaaa"
        assertTrue(
            "repeats should score lower",
            StrengthEstimator.score(repeated) < StrengthEstimator.score(varied),
        )
    }
}