package com.aegis.manager.data.model

/** The kinds of record the vault stores. */
enum class ItemType { LOGIN, CARD, NOTE }

data class VaultItem(
    val id: Long = 0L,
    val type: ItemType = ItemType.LOGIN,
    val title: String = "",
    val username: String = "",
    val password: String = "",
    val website: String = "",
    val notes: String = "",
    val cardNumber: String = "",
    val cardHolder: String = "",
    val expiry: String = "",
    val cvv: String = "",
    val favourite: Boolean = false,
    val hasTwoFactor: Boolean = false,
    val updatedAt: Long = 0L,
) {
    /** Secondary line shown in list rows. */
    val subtitle: String
        get() = when (type) {
            ItemType.LOGIN -> username.ifBlank { website }
            ItemType.CARD -> cardHolder
            ItemType.NOTE -> notes.lineSequence().firstOrNull().orEmpty()
        }
}