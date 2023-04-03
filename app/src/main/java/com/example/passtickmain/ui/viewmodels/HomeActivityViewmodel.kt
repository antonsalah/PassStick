package com.example.passtickmain.ui.viewmodels

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope

import com.example.passtickmain.data.*

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toList


//wip
class HomeActivityViewmodel(private val accountDao: AccountDao): ViewModel() {
    var fullAccountList: StateFlow<List<Account>> = accountDao.getAllAccounts()
        .stateIn(
            initialValue = Loading,
            scope = viewModelScope,
            started = WhileSubscribed(5000)

        )

    var singleAccount: StateFlow<Account> = accountDao.getAccount()

}