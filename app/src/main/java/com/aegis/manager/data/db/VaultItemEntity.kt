package com.aegis.manager.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aegis.manager.data.model.ItemType
import com.aegis.manager.data.model.VaultItem

@Entity(tableName = "vault_items")
data class VaultItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val type: String = ItemType.LOGIN.name,
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
    @ColumnInfo(name = "updated_at") val updatedAt: Long = 0L,
)

fun VaultItemEntity.toModel(): VaultItem = VaultItem(
    id = id,
    type = runCatching { ItemType.valueOf(type) }.getOrDefault(ItemType.LOGIN),
    title = title,
    username = username,
    password = password,
    website = website,
    notes = notes,
    cardNumber = cardNumber,
    cardHolder = cardHolder,
    expiry = expiry,
    cvv = cvv,
    favourite = favourite,
    hasTwoFactor = hasTwoFactor,
    updatedAt = updatedAt,
)

fun VaultItem.toEntity(): VaultItemEntity = VaultItemEntity(
    id = id,
    type = type.name,
    title = title,
    username = username,
    password = password,
    website = website,
    notes = notes,
    cardNumber = cardNumber,
    cardHolder = cardHolder,
    expiry = expiry,
    cvv = cvv,
    favourite = favourite,
    hasTwoFactor = hasTwoFactor,
    updatedAt = updatedAt,
)
