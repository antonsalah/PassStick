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

package com.passtick.test.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import com.passtick.test.data.AccountRepository
import com.passtick.test.data.DefaultAccountRepository
import com.passtick.test.data.local.database.Account
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindsAccountRepository(
        accountRepository: DefaultAccountRepository
    ): AccountRepository
}
/*
class FakeAccountRepository @Inject constructor() : AccountRepository {
    override val accounts: Flow<List<Account>> = flowOf(fakeAccounts)

    override suspend fun delete(account: Account) {
        throw NotImplementedError()
    }
    override suspend fun add(account: Account) {
        throw NotImplementedError()
    }

    override suspend fun update(account: Account) {
        throw NotImplementedError()
    }

    suspend fun queryByServiceName(account: Account) {
        throw NotImplementedError()
    }
}

val fakeAccounts = listOf(Account("One", "Two", "Three"))
*/