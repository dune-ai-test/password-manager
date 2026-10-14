package com.aegis.manager.ui.screens.security

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aegis.manager.data.repo.VaultRepository
import com.aegis.manager.domain.SecurityAuditor
import com.aegis.manager.domain.SecurityReport
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The Security screen audits the real vault on every change, so the score,
 * counts and lists can never drift from what is actually stored.
 */
@HiltViewModel
class SecurityViewModel @Inject constructor(
    private val repository: VaultRepository,
) : ViewModel() {

    val report: StateFlow<SecurityReport> = repository.observeAll()
        .map { SecurityAuditor.audit(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SecurityAuditor.audit(emptyList()),
        )

    init {
        viewModelScope.launch { repository.seedIfEmpty() }
    }
}