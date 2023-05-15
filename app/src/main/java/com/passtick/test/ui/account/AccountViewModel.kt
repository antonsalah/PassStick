/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.passtick.test.ui.account

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.passtick.test.data.AccountRepository
import com.passtick.test.data.local.database.Account
import com.passtick.test.ui.account.AccountUiState.Error
import com.passtick.test.ui.account.AccountUiState.Loading
import com.passtick.test.ui.account.AccountUiState.Success
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {
    private val fakeAccounts: List<Account> = emptyList()

    val uiState: StateFlow<AccountUiState> = accountRepository
        .accounts.map<List<Account>, AccountUiState>(::Success)
        .catch { emit(Error(it)) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)

    var textState = mutableStateOf("")
    var filteredAccounts = mutableStateOf(fakeAccounts)

    fun addAccount(accountToAdd: Account) {
        viewModelScope.launch {
            accountRepository.add(accountToAdd)
        }
        updateQuery("")
    }
    fun deleteAccount(accountToDelete: Account) {
        viewModelScope.launch {
            accountRepository.delete(accountToDelete)
        }
        updateQuery("")
    }

    fun updateAccount(
        accountToUpdate: Account,
        newService:  String = accountToUpdate.serviceName,
        newUsername: String = accountToUpdate.username,
        newPassword: String = accountToUpdate.password)
    {
        val id: Int = accountToUpdate.uid

        viewModelScope.launch{
            accountRepository.update(
                Account(
                    uid = id,
                    serviceName = newService,
                    username = newUsername,
                    password = newPassword,
                )
            )
        }

        updateQuery("")
    }

    fun updateQuery(query: String) {
        viewModelScope.launch {
            filteredAccounts.value = accountRepository
                .getAccountByServiceName(query)
                .first()
        }
        textState.value = query
    }
}

sealed interface AccountUiState {
    object Loading : AccountUiState
    data class Error(val throwable: Throwable) : AccountUiState
    data class Success(val data: List<Account>) : AccountUiState
}
