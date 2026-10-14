package com.aegis.manager.data.repo

import com.aegis.manager.data.crypto.CryptoBox
import com.aegis.manager.data.db.VaultDao
import com.aegis.manager.data.db.toEntity
import com.aegis.manager.data.db.toModel
import com.aegis.manager.data.model.ItemType
import com.aegis.manager.data.model.VaultItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VaultRepository @Inject constructor(
    private val dao: VaultDao,
    private val cryptoBox: CryptoBox,
) {

    fun observeAll(): Flow<List<VaultItem>> =
        dao.observeAll().map { rows -> rows.map { it.toModel() } }

    fun observeById(id: Long): Flow<VaultItem?> =
        dao.observeById(id).map { it?.toModel() }

    suspend fun find(id: Long): VaultItem? = dao.findById(id)?.toModel()

    suspend fun count(): Int = dao.count()

    /**
     * Secrets are written through [CryptoBox] on the way in and read back
     * through it on the way out, so the demo build round-trips cleanly today
     * and gets encryption for free once the box is swapped.
     */
    suspend fun upsert(item: VaultItem): Long {
        val sealed = item.copy(
            password = cryptoBox.seal(item.password),
            notes = cryptoBox.seal(item.notes),
            cardNumber = cryptoBox.seal(item.cardNumber),
            cvv = cryptoBox.seal(item.cvv),
        )
        val entity = sealed.toEntity()
        return if (item.id == 0L) {
            dao.insert(entity)
        } else {
            dao.update(entity)
            item.id
        }
    }

    suspend fun delete(item: VaultItem) {
        dao.delete(item.copy(
            password = cryptoBox.seal(item.password),
            notes = cryptoBox.seal(item.notes),
            cardNumber = cryptoBox.seal(item.cardNumber),
            cvv = cryptoBox.seal(item.cvv),
        ).toEntity())
    }

    /** Fills an empty vault with the sample set the design is built around. */
    suspend fun seedIfEmpty() {
        if (dao.count() > 0) return
        dao.insertAll(SampleData.items.map { it.toEntity() })
    }

    suspend fun search(query: String): List<VaultItem> {
        val needle = query.trim().lowercase()
        if (needle.isEmpty()) return emptyList()
        return dao.findAllOnce()
            .map { it.toModel() }
            .filter { item ->
                item.title.lowercase().contains(needle) ||
                    item.username.lowercase().contains(needle) ||
                    item.website.lowercase().contains(needle) ||
                    item.notes.lowercase().contains(needle)
            }
    }
}
