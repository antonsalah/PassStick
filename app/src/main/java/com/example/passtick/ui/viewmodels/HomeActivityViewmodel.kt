package com.example.passtick.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.passtick.data.*
import kotlinx.coroutines.flow.*

import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed


//wip
class HomeActivityViewmodel(private val accountDao: AccountDao): ViewModel() {

    var fullAccountList: StateFlow<FullAccountList> =
        accountDao.getAllAccounts().map { FullAccountList(it) }
            .stateIn(
                scope = viewModelScope,
                started = WhileSubscribed(5000),
                initialValue = FullAccountList()
            )

}

data class FullAccountList(val itemList: List<Account> = listOf())