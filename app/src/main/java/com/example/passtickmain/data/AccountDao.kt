package com.example.passtickmain.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao //Data Access Object
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(account: Account)

    @Update
    suspend fun update(account: Account)

    @Delete
    suspend fun delete(account: Account)

    @Query("SELECT * FROM accounts")
    fun getAllAccounts(): Flow<List<Account>>

    @Query("SELECT * FROM accounts WHERE id = accountId")
    fun getAccount(accountId: Int): Flow<Account>
}

