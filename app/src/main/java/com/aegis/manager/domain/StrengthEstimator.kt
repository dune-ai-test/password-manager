package com.aegis.manager.domain

enum class Strength { WEAK, FAIR, GOOD, STRONG, EXCELLENT }

/**
 * Password strength scoring shared by the Create-master-password screen, the
 * Generator screen and the Security audit.
 *
 * Pure and dependency free so it can be unit tested on the JVM.
 */
object StrengthEstimator {

    private val SEQUENCES = listOf("abcdefghijklmnopqrstuvwxyz", "01234567890", "qwertyuiop")

    fun estimate(password: String): Strength = strengthOf(score(password))

    fun strengthOf(score: Int): Strength = when {
        score >= 85 -> Strength.EXCELLENT
        score >= 70 -> Strength.STRONG
        score >= 50 -> Strength.GOOD
        score >= 30 -> Strength.FAIR
        else -> Strength.WEAK
    }

    /** Returns a 0..100 score. */
    fun score(password: String): Int {
        if (password.isEmpty()) return 0

        var points = 0

        // Length is the dominant factor.
        points += when {
            password.length >= 20 -> 40
            password.length >= 16 -> 34
            password.length >= 12 -> 27
            password.length >= 10 -> 20
            password.length >= 8 -> 13
            password.length >= 6 -> 7
            else -> 2
        }

        // Character class variety.
        val classes = buildList {
            if (password.any { it.isLowerCase() }) add(1)
            if (password.any { it.isUpperCase() }) add(1)
            if (password.any { it.isDigit() }) add(1)
            if (password.any { !it.isLetterOrDigit() }) add(1)
        }
        points += classes.size * 8

        // Variety beyond the bare minimum.
        val distinctRatio = password.toSet().size.toFloat() / password.length
        points += (distinctRatio * 14).toInt()

        // Penalties for the usual sins.
        if (containsSequence(password)) points -= 18
        if (hasRunOfRepeats(password, 3)) points -= 14
        if (COMMON.contains(password.lowercase())) return 4

        return points.coerceIn(0, 100)
    }

    private fun containsSequence(password: String): Boolean {
        val lower = password.lowercase()
        return SEQUENCES.any { seq ->
            (2 until seq.length).any { len ->
                (0..seq.length - len).any { start ->
                    val candidate = seq.substring(start, start + len)
                    candidate.length >= 3 && lower.contains(candidate)
                }
            }
        }
    }

    private fun hasRunOfRepeats(password: String, run: Int): Boolean {
        var current = 1
        for (i in 1 until password.length) {
            current = if (password[i] == password[i - 1]) current + 1 else 1
            if (current >= run) return true
        }
        return false
    }

    private val COMMON = setOf(
        "password", "123456", "12345678", "qwerty", "abc123", "letmein", "monkey",
        "111111", "iloveyou", "admin", "welcome", "login", "master", "dragon",
        "summer21", "passw0rd", "shadow", "sunshine", "princess", "football",
    )
}