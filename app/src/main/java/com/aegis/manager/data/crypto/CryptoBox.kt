package com.aegis.manager.data.crypto

/**
 * The seam where real encryption will land.
 *
 * This build is a demo: the vault is a plain local Room database, so the box
 * passes values through untouched. Everything that reads or writes a secret
 * goes through this interface, so swapping in AES-GCM + AndroidKeyStore later
 * means providing one new implementation and changing one Hilt binding - no
 * call site has to change.
 */
interface CryptoBox {
    /** Plaintext to stored form. */
    fun seal(plain: String): String

    /** Stored form back to plaintext. */
    fun open(sealed: String): String
}

/** Demo implementation: identity transform. */
class PlaintextCryptoBox : CryptoBox {
    override fun seal(plain: String): String = plain
    override fun open(sealed: String): String = sealed
}