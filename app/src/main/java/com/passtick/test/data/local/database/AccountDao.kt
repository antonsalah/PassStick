package com.passtick.test.data.local.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

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