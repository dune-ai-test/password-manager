package com.aegis.manager.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.random.Random

class PasswordGeneratorTest {

    private fun options(
        length: Int = 20,
        uppercase: Boolean = true,
        lowercase: Boolean = true,
        numbers: Boolean = true,
        symbols: Boolean = true,
        avoidLookAlikes: Boolean = false,
        mode: GeneratorMode = GeneratorMode.RANDOM,
    ) = GeneratorOptions(
        length = length,
        uppercase = uppercase,
        lowercase = lowercase,
        numbers = numbers,
        symbols = symbols,
        avoidLookAlikes = avoidLookAlikes,
        mode = mode,
    )

    @Test
    fun `respects the requested length`() {
        val generator = PasswordGenerator(Random(7))
        listOf(8, 12, 20, 32, 64).forEach { length ->
            val result = generator.generate(options(length = length))
            assertEquals(length, result.length)
        }
    }

    @Test
    fun `includes every enabled character class`() {
        val generator = PasswordGenerator(Random(42))
        repeat(50) {
            val result = generator.generate(options(length = 24))
            assertTrue(result.any { it.isLowerCase() })
            assertTrue(result.any { it.isUpperCase() })
            assertTrue(result.any { it.isDigit() })
            assertTrue(result.any { !it.isLetterOrDigit() })
        }
    }

    @Test
    fun `omits disabled character classes`() {
        val generator = PasswordGenerator(Random(11))
        repeat(50) {
            val result = generator.generate(
                options(length = 16, uppercase = false, symbols = false),
            )
            assertTrue(result.none { it.isUpperCase() })
            assertTrue(result.none { !it.isLetterOrDigit() })
        }
    }

    @Test
    fun `avoid lookalikes strips the confusable characters`() {
        val generator = PasswordGenerator(Random(3))
        repeat(50) {
            val result = generator.generate(options(length = 32, avoidLookAlikes = true))
            assertTrue(
                "found a look-alike in $result",
                result.none { it in PasswordGenerator.LOOK_ALIKES },
            )
        }
    }

    @Test
    fun `is deterministic for a fixed seed`() {
        val a = PasswordGenerator(Random(1234)).generate(options())
        val b = PasswordGenerator(Random(1234)).generate(options())
        assertEquals(a, b)
    }

    @Test
    fun `different seeds give different passwords`() {
        val a = PasswordGenerator(Random(1)).generate(options())
        val b = PasswordGenerator(Random(2)).generate(options())
        assertTrue(a != b)
    }

    @Test
    fun `memorable mode produces a readable phrase`() {
        val generator = PasswordGenerator(Random(99))
        repeat(20) {
            val result = generator.generate(options(length = 20, mode = GeneratorMode.MEMORABLE))
            assertEquals(20, result.length)
            assertTrue("expected letters in $result", result.any { it.isLetter() })
            assertTrue("expected a separator in $result", result.any { it == '.' || it == '-' })
        }
    }

    @Test
    fun `no class selected yields an empty password`() {
        val generator = PasswordGenerator(Random(5))
        val result = generator.generate(
            options(uppercase = false, lowercase = false, numbers = false, symbols = false),
        )
        assertEquals("", result)
    }

    @Test
    fun `length below the floor is clamped`() {
        val generator = PasswordGenerator(Random(6))
        assertEquals(
            PasswordGenerator.MIN_LENGTH,
            generator.generate(options(length = 1)).length,
        )
    }
}