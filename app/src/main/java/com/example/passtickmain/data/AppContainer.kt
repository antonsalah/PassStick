package com.example.passtickmain.data

import android.content.Context

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val accountRepository: AccountRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineAccountRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {

    override val accountRepository: AccountRepository by lazy {
        OfflineAccountRepository(AccountDatabase.getDatabase(context).accountDao())
    }
}