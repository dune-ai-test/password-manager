package com.aegis.manager.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aegis.manager.data.model.ItemType
import com.aegis.manager.data.model.VaultItem
import com.aegis.manager.data.repo.VaultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchState(
    val query: String = "",
    val results: List<VaultItem> = emptyList(),
    val filterAll: Boolean = true,
)

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: VaultRepository,
) : ViewModel() {

    private val query = MutableStateFlow("")
    private val filterAll = MutableStateFlow(true)

    val state: StateFlow<SearchState> =
        combine(repository.observeAll(), query, filterAll) { all, q, allFilter ->
            val needle = q.trim().lowercase()
            SearchState(
                query = q,
                results = if (needle.isEmpty()) {
                    emptyList()
                } else {
                    all.asSequence()
                        .filter { allFilter || it.type == ItemType.LOGIN }
                        .filter { item ->
                            item.title.lowercase().contains(needle) ||
                                item.username.lowercase().contains(needle) ||
                                item.website.lowercase().contains(needle) ||
                                item.notes.lowercase().contains(needle)
                        }
                        .toList()
                },
                filterAll = allFilter,
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), SearchState())

    init {
        viewModelScope.launch { repository.seedIfEmpty() }
    }

    fun onQueryChange(value: String) {
        query.value = value
    }

    fun showAll() {
        filterAll.value = true
    }

    fun showLoginsOnly() {
        filterAll.value = false
    }
}
