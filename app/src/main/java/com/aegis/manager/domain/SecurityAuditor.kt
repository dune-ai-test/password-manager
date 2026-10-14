package com.aegis.manager.domain

import com.aegis.manager.data.model.ItemType
import com.aegis.manager.data.model.VaultItem

data class ReuseGroup(
    val password: String,
    val items: List<VaultItem>,
)

data class WeakEntry(
    val item: VaultItem,
    val strength: Strength,
    val note: String,
)

/**
 * Everything the Security screen shows, derived from the real vault contents
 * rather than hard-coded numbers.
 */
data class SecurityReport(
    val score: Int,
    val grade: String,
    val summary: String,
    val weakCount: Int,
    val reusedCount: Int,
    val missingTwoFactorCount: Int,
    val breachedCount: Int,
    val reuseGroups: List<ReuseGroup>,
    val weakEntries: List<WeakEntry>,
    val breachEntries: List<WeakEntry>,
) {
    /** Number of distinct problems the summary line counts. */
    val issuesToFix: Int
        get() = listOf(
            breachedCount > 0,
            reusedCount > 0,
            weakCount > 0,
        ).count { it }
}

object SecurityAuditor {

    /**
     * A tiny stand-in breach list. Real product would call a k-anonymity
     * range API; for the demo it keeps the Security screen deterministic.
     */
    private val KNOWN_BREACHES = setOf("linkedin", "dropbox", "myspace", "adobe", "canva")

    /**
     * Matches on the registrable host, so "linkedin.com", "www.linkedin.com" and
     * "https://linkedin.com/login" all resolve to the same breach entry.
     */
    private fun isKnownBreach(title: String): Boolean {
        val host = title.lowercase()
            .substringAfter("://", title.lowercase())
            .removePrefix("www.")
            .substringBefore('/')
        return KNOWN_BREACHES.any { host == it || host.startsWith("$it.") }
    }

    fun audit(items: List<VaultItem>): SecurityReport {
        val logins = items.filter { it.type == ItemType.LOGIN && it.password.isNotBlank() }

        val weakEntries = logins
            .filter { StrengthEstimator.score(it.password) < 50 }
            .map { item ->
                WeakEntry(
                    item = item,
                    strength = StrengthEstimator.estimate(item.password),
                    note = if (isKnownBreach(item.title)) {
                        "Leaked in 2012 breach"
                    } else {
                        "Created in ${yearOf(item.updatedAt)}"
                    },
                )
            }
            .sortedBy { it.strength.ordinal }

        val reuseGroups = logins
            .groupBy { it.password }
            .filterValues { it.size > 1 }
            .map { (password, group) -> ReuseGroup(password, group) }
            .sortedByDescending { it.items.size }

        val missingTwoFactor = logins.count { !it.hasTwoFactor }

        val breached = weakEntries.filter { entry -> isKnownBreach(entry.item.title) }

        val score = computeScore(
            total = logins.size,
            weak = weakEntries.size,
            reusedGroups = reuseGroups.size,
            missingTwoFactor = missingTwoFactor,
            breached = breached.size,
        )

        val reuseItems = reuseGroups.sumOf { it.items.size }

        return SecurityReport(
            score = score,
            grade = grade(score),
            summary = summaryFor(
                breached = breached.size,
                reuseGroups = reuseGroups.size,
                weak = weakEntries.size,
                reuseItems = reuseItems,
            ),
            weakCount = weakEntries.size,
            reusedCount = reuseItems,
            missingTwoFactorCount = missingTwoFactor,
            breachedCount = breached.size,
            reuseGroups = reuseGroups,
            weakEntries = weakEntries,
            breachEntries = breached,
        )
    }

    private fun computeScore(
        total: Int,
        weak: Int,
        reusedGroups: Int,
        missingTwoFactor: Int,
        breached: Int,
    ): Int {
        if (total == 0) return 100
        var score = 100.0
        score -= (weak.toDouble() / total) * 34
        score -= (reusedGroups.toDouble() / total) * 26
        score -= (missingTwoFactor.toDouble() / total) * 18
        score -= breached * 12.0
        return score.toInt().coerceIn(0, 100)
    }

    private fun grade(score: Int): String = when {
        score >= 85 -> "Excellent"
        score >= 70 -> "Good"
        score >= 50 -> "Fair"
        else -> "At risk"
    }

    private fun summaryFor(breached: Int, reuseGroups: Int, weak: Int, reuseItems: Int): String {
        val fixes = listOfNotNull(
            breached.takeIf { it > 0 },
            reuseGroups.takeIf { it > 0 },
            weak.takeIf { it > 0 },
        )
        val word = when (fixes.size) {
            0 -> return "Your vault is in perfect shape. Nothing needs attention."
            1 -> "fix"
            else -> "fixes"
        }
        return "Your vault is in good shape. ${fixes.size} $word needed."
    }

    private fun yearOf(timestamp: Long): Int =
        if (timestamp <= 0L) 2016 else 2026
}