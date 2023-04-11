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

package com.passtick.test.data.local.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity
data class Account(
    var username: String,
    var password: String,
    var serviceName: String
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}

@Dao
interface AccountDao {
    @Query("SELECT * FROM account ORDER BY uid DESC LIMIT 1000")
    fun getAccounts(): Flow<List<Account>>

    @Insert
    suspend fun insertAccount(item: Account)

    @Delete
    suspend fun deleteAccount(item: Account)

    @Update
    suspend fun updateAccount(item: Account)

    @Query("SELECT * FROM account WHERE serviceName LIKE :serviceName")
    fun getAccountByServiceName(serviceName: String): Flow<List<Account>>

    @Query("SELECT * FROM account WHERE uid = :id")
    fun getAccount(id: Int): Flow<Account>
}
