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

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.passtick.test.data.AccountRepository
import com.passtick.test.data.local.database.Account
import com.passtick.test.ui.account.AccountUiState.Error
import com.passtick.test.ui.account.AccountUiState.Loading
import com.passtick.test.ui.account.AccountUiState.Success
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {

    var textState = mutableStateOf(TextFieldValue(""))
    val dummy: List<Account> = emptyList()
    var filteredAccounts = mutableStateOf(dummy)
    val uiState: StateFlow<AccountUiState> = accountRepository
        .accounts.map<List<Account>, AccountUiState>(::Success)
        .catch { emit(Error(it)) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)



    fun addAccount(accountToAdd: Account) {
        viewModelScope.launch {
            accountRepository.add(accountToAdd)
        }
    }
    fun deleteAccount(accountToDelete: Account) {
        viewModelScope.launch {
            accountRepository.delete(accountToDelete)
        }
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
    }

    fun resetSearchField() {
        textState.value = TextFieldValue("")
    }
    fun init(value: List<Account>) {
        filteredAccounts.value = value
    }
}

sealed interface AccountUiState {
    object Loading : AccountUiState
    data class Error(val throwable: Throwable) : AccountUiState
    data class Success(val data: List<Account>) : AccountUiState
}
