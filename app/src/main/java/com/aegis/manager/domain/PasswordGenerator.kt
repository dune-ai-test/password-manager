package com.aegis.manager.domain

import kotlin.random.Random

enum class GeneratorMode { RANDOM, MEMORABLE }

data class GeneratorOptions(
    val length: Int = 20,
    val uppercase: Boolean = true,
    val lowercase: Boolean = true,
    val numbers: Boolean = true,
    val symbols: Boolean = true,
    val avoidLookAlikes: Boolean = false,
    val mode: GeneratorMode = GeneratorMode.RANDOM,
)

/**
 * Password generator behind the Generator screen.
 *
 * Takes an injectable [Random] so the unit tests can pin the seed and assert on
 * exact output instead of fuzzy-matching.
 */
class PasswordGenerator(private val random: Random = Random.Default) {

    fun generate(options: GeneratorOptions): String =
        if (options.mode == GeneratorMode.MEMORABLE) {
            memorable(options, random)
        } else {
            randomPassword(options, random)
        }

    private fun randomPassword(options: GeneratorOptions, rnd: Random): String {
        val pool = buildString {
            if (options.lowercase) append(LOWERCASE)
            if (options.uppercase) append(UPPERCASE)
            if (options.numbers) append(NUMBERS)
            if (options.symbols) append(SYMBOLS)
        }.let { if (options.avoidLookAlikes) it.filterNot { c -> c in LOOK_ALIKES } else it }

        if (pool.isEmpty()) return ""

        val length = options.length.coerceAtLeast(MIN_LENGTH)
        val out = StringBuilder(length)

        // Guarantee one character from every enabled class so a "20 char"
        // password is never accidentally all-lowercase.
        if (options.lowercase) out.append(filtered(LOWERCASE, options).random(rnd))
        if (options.uppercase) out.append(filtered(UPPERCASE, options).random(rnd))
        if (options.numbers) out.append(filtered(NUMBERS, options).random(rnd))
        if (options.symbols) out.append(filtered(SYMBOLS, options).random(rnd))

        while (out.length < length) {
            out.append(pool.random(rnd))
        }

        // Shuffle so the guaranteed characters are not always in front.
        return shuffle(out.toString(), rnd)
    }

    /** Fisher-Yates over the characters, so the shuffle does not depend on a
     *  particular stdlib extension signature. */
    private fun shuffle(chars: String, rnd: Random): String {
        val list = chars.toMutableList()
        for (i in list.lastIndex downTo 1) {
            val j = rnd.nextInt(i + 1)
            val tmp = list[i]
            list[i] = list[j]
            list[j] = tmp
        }
        return list.joinToString("")
    }

    private fun memorable(options: GeneratorOptions, rnd: Random): String {
        val target = options.length.coerceAtLeast(MIN_LENGTH)
        val out = StringBuilder()

        while (out.length + 1 < target) {
            val word = WORDS.random(rnd)
            if (out.isNotEmpty() && out.length + 1 + word.length > target) break
            if (out.isNotEmpty()) out.append(if (rnd.nextBoolean()) '.' else '-')
            out.append(
                if (options.uppercase) {
                    word.replaceFirstChar { it.uppercaseChar() }
                } else {
                    word
                },
            )
        }

        while (out.length < target - 2) {
            out.append(WORDS.random(rnd).take(2))
        }
        while (out.length < target) {
            out.append(NUMBERS.random(rnd))
        }
        return out.toString()
    }

    private fun filtered(source: String, options: GeneratorOptions): String =
        if (options.avoidLookAlikes) {
            source.filterNot { it in LOOK_ALIKES }
        } else {
            source
        }

    companion object {
        const val MIN_LENGTH = 4
        const val MAX_LENGTH = 64

        const val LOWERCASE = "abcdefghijklmnopqrstuvwxyz"
        const val UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        const val NUMBERS = "0123456789"
        const val SYMBOLS = "!@#$%^&*()-_=+[]{}<>?/"

        /** I l 1 O 0 o S 5 B 8 Z 2 rn m - the glyphs people genuinely mistype. */
        const val LOOK_ALIKES = "Il1O0oS5B8Z2|"

        private val WORDS = listOf(
            "amber", "basalt", "cedar", "delta", "ember", "fjord", "glacier", "harbor",
            "indigo", "juniper", "kestrel", "lantern", "meadow", "nimbus", "onyx", "pebble",
            "quartz", "ripple", "summit", "thistle", "umbra", "violet", "willow", "zephyr",
            "anchor", "bramble", "cobalt", "dune", "echo", "flint", "garnet", "hollow",
            "ivory", "jasper", "kelp", "lumen", "moss", "nectar", "opal", "prairie",
        )
    }
}