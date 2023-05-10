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

package com.passtick.test.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.passtick.test.data.local.database.Account
import com.passtick.test.data.local.database.AccountDao
import javax.inject.Inject

interface AccountRepository {
    val accounts: Flow<List<Account>>
    suspend fun delete(account: Account)
    suspend fun add(account: Account)
    suspend fun update(account: Account)
    suspend fun getAccountByServiceName(serviceName: String): Flow<List<Account>>
}

class DefaultAccountRepository @Inject constructor(
    private val accountDao: AccountDao
) : AccountRepository {

    override val accounts: Flow<List<Account>> =
        accountDao.getAccounts().map { items -> items.map { it } }

    override suspend fun add(account: Account) {
        accountDao.insertAccount(account)
    }

    override suspend fun delete(account: Account) {
        accountDao.deleteAccount(account)
    }

    override suspend fun update(account: Account){
        accountDao.updateAccount(account)
    }

    override suspend fun getAccountByServiceName(serviceName : String) : Flow<List<Account>>{
       return accountDao.getAccountByServiceName(serviceName).map {items -> items.map {it}}
    }
}
