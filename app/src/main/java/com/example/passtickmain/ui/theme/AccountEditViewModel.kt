package com.example.passtickmain.ui.theme


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.passtickmain.data.AccountRepository

/**
 * ViewModel to retrieve and update an item from the [AccountRepository]'s data source.
 */
class AccountEntryViewModel(private val accountRepository: AccountRepository) : ViewModel(){
}

class AccountEditViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    /**
     * Holds current item ui state
     */
    var AccountUiState by mutableStateOf(AccountUiState())
        private set

    private val itemId: Int = checkNotNull(savedStateHandle[AccountEditDestination.accountIdArg])

}