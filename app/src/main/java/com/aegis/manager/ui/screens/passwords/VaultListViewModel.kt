package com.aegis.manager.ui.screens.passwords

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aegis.manager.data.model.ItemType
import com.aegis.manager.data.model.VaultItem
import com.aegis.manager.data.repo.VaultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Shared by the Passwords and Category screens - both show the same vault
 * contents, so both observe the same repository flow.
 */
@HiltViewModel
class VaultListViewModel @Inject constructor(
    private val repository: VaultRepository,
) : ViewModel() {

    val logins: StateFlow<List<VaultItem>> = repository.observeAll()
        .map { items -> items.filter { it.type == ItemType.LOGIN } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val all: StateFlow<List<VaultItem>> = repository.observeAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    init {
        viewModelScope.launch { repository.seedIfEmpty() }
    }

    fun delete(item: VaultItem) {
        viewModelScope.launch { repository.delete(item) }
    }
}