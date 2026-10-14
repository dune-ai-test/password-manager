package com.aegis.manager.domain

import com.aegis.manager.data.model.ItemType
import com.aegis.manager.data.model.VaultItem
import com.aegis.manager.data.repo.SampleData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SecurityAuditorTest {

    private fun login(
        title: String,
        password: String,
        twoFactor: Boolean = false,
    ) = VaultItem(
        id = title.hashCode().toLong(),
        type = ItemType.LOGIN,
        title = title,
        password = password,
        hasTwoFactor = twoFactor,
    )

    @Test
    fun `an empty vault scores full marks`() {
        val report = SecurityAuditor.audit(emptyList())
        assertEquals(100, report.score)
        assertEquals(0, report.weakCount)
        assertEquals(0, report.reusedCount)
        assertEquals(0, report.issuesToFix)
    }

    @Test
    fun `passwords shared across items are counted as reuse`() {
        val report = SecurityAuditor.audit(
            listOf(
                login("a.com", "Summer21!"),
                login("b.com", "Summer21!"),
                login("c.com", "Summer21!"),
                login("d.com", "Xk9#mP2vTq!Lz4W", twoFactor = true),
            ),
        )
        assertEquals(3, report.reusedCount)
        assertEquals(1, report.reuseGroups.size)
        assertEquals(3, report.reuseGroups.first().items.size)
    }

    @Test
    fun `weak passwords are listed`() {
        val report = SecurityAuditor.audit(
            listOf(
                login("weak.com", "123456"),
                login("strong.com", "Xk9#mP2vTq!Lz4W", twoFactor = true),
            ),
        )
        assertEquals(1, report.weakCount)
        assertEquals("weak.com", report.weakEntries.first().item.title)
    }

    @Test
    fun `missing two factor is counted per login`() {
        val report = SecurityAuditor.audit(
            listOf(
                login("a.com", "Xk9#mP2vTq!Lz4W", twoFactor = true),
                login("b.com", "Wq3\$bN8@rTz1%y"),
                login("c.com", "Lp5\$cM2^dXh7@z"),
            ),
        )
        assertEquals(2, report.missingTwoFactorCount)
    }

    @Test
    fun `known breached hosts are flagged`() {
        val report = SecurityAuditor.audit(
            listOf(
                login("linkedin.com", "linkedin"),
                login("dropbox.com", "dropbox2016"),
            ),
        )
        assertEquals(2, report.breachedCount)
        assertEquals(2, report.breachEntries.size)
    }

    @Test
    fun `score degrades as problems accumulate`() {
        val clean = SecurityAuditor.audit(
            List(10) { login("site$it.com", "Xk9#mP2vTq!L${it}z4W", twoFactor = true) },
        )
        val dirty = SecurityAuditor.audit(
            listOf(
                login("linkedin.com", "linkedin"),
                login("a.com", "Summer21!"),
                login("b.com", "Summer21!"),
                login("c.com", "123456"),
            ),
        )
        assertTrue("expected clean > dirty", clean.score > dirty.score)
    }

    @Test
    fun `the summary names the number of fixes`() {
        val report = SecurityAuditor.audit(SampleData.items)
        assertTrue(report.summary.contains("${report.issuesToFix}"))
    }

    @Test
    fun `notes and cards are not treated as logins`() {
        val report = SecurityAuditor.audit(
            listOf(
                VaultItem(id = 1, type = ItemType.NOTE, title = "Note", notes = "hello"),
                VaultItem(id = 2, type = ItemType.CARD, title = "Card", cardHolder = "Alex"),
            ),
        )
        assertEquals(0, report.missingTwoFactorCount)
        assertEquals(100, report.score)
    }

    @Test
    fun `grade follows the score`() {
        assertEquals("Excellent", SecurityAuditor.audit(emptyList()).grade)
    }
}
