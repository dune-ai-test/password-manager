package com.aegis.manager.data.repo

import com.aegis.manager.data.model.ItemType
import com.aegis.manager.data.model.VaultItem

/**
 * The sample vault the design is drawn around.
 *
 * Seeded only on first launch so the Security screen has something real to
 * audit: five accounts genuinely share a password, and a few genuinely score
 * weak, so every number on that screen is computed rather than hard-coded.
 */
object SampleData {

    private const val SHARED = "Summer21!"

    val items: List<VaultItem> = listOf(
        VaultItem(
            type = ItemType.LOGIN,
            title = "github.com",
            username = "alex@meridian.io",
            password = "9kT\$vQ2!xLm7#pRd",
            website = "https://github.com",
            notes = "Recovery codes saved in Safe #4.",
            hasTwoFactor = true,
            updatedAt = 1_690_000_000_000,
        ),
        VaultItem(
            type = ItemType.LOGIN,
            title = "mail.google.com",
            username = "alex@meridian.io",
            password = "Lx4\$nBv8@qTz1%w",
            website = "https://mail.google.com",
            hasTwoFactor = true,
            updatedAt = 1_690_000_000_000,
        ),
        VaultItem(
            type = ItemType.LOGIN,
            title = "figma.com",
            username = "alex@meridian.io",
            password = "wR7\$jKd3^pHn9@ye",
            website = "https://figma.com",
            notes = "Work account",
            updatedAt = 1_690_000_000_000,
        ),
        VaultItem(
            type = ItemType.LOGIN,
            title = "chase.com",
            username = "amorgan_92",
            password = SHARED,
            website = "https://chase.com",
            hasTwoFactor = true,
            updatedAt = 1_690_000_000_000,
        ),
        VaultItem(
            type = ItemType.LOGIN,
            title = "netflix.com",
            username = "alex@meridian.io",
            password = SHARED,
            website = "https://netflix.com",
            updatedAt = 1_690_000_000_000,
        ),
        VaultItem(
            type = ItemType.LOGIN,
            title = "notion.so",
            username = "alex@meridian.io",
            password = SHARED,
            website = "https://notion.so",
            updatedAt = 1_690_000_000_000,
        ),
        VaultItem(
            type = ItemType.LOGIN,
            title = "amazon.com",
            username = "amorgan_92",
            password = SHARED,
            website = "https://amazon.com",
            updatedAt = 1_690_000_000_000,
        ),
        VaultItem(
            type = ItemType.LOGIN,
            title = "spotify.com",
            username = "alex@meridian.io",
            password = SHARED,
            website = "https://spotify.com",
            updatedAt = 1_690_000_000_000,
        ),
        VaultItem(
            type = ItemType.LOGIN,
            title = "discord.com",
            username = "xalexm",
            password = SHARED,
            website = "https://discord.com",
            updatedAt = 1_690_000_000_000,
        ),
        VaultItem(
            type = ItemType.LOGIN,
            title = "linkedin.com",
            username = "alex.morgan@work.io",
            password = "linkedin",
            website = "https://linkedin.com",
            updatedAt = 1_135_000_000_000,
        ),
        VaultItem(
            type = ItemType.LOGIN,
            title = "dropbox.com",
            username = "alex@meridian.io",
            password = "dropbox2016",
            website = "https://dropbox.com",
            updatedAt = 1_476_000_000_000,
        ),
        VaultItem(
            type = ItemType.LOGIN,
            title = "myspace.com",
            username = "amorgan_92",
            password = "123456",
            website = "https://myspace.com",
            updatedAt = 1_100_000_000_000,
        ),
        VaultItem(
            type = ItemType.NOTE,
            title = "Recovery codes",
            username = "",
            password = "",
            notes = "Backup codes for the work account. Ask Dana before resetting.",
            updatedAt = 1_690_000_000_000,
        ),
        VaultItem(
            type = ItemType.CARD,
            title = "Amex Platinum",
            username = "Alex Morgan",
            cardNumber = "3782 822463 10005",
            cardHolder = "ALEX MORGAN",
            expiry = "04/28",
            cvv = "1234",
            updatedAt = 1_690_000_000_000,
        ),
    )
}
