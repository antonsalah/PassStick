package com.example.passtickmain

import androidx.room.*
import com.example.passtickmain.data.Account
import kotlinx.coroutines.flow.Flow

@Dao //Data Access Object
interface accountDao {

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

    @Query("SELECT * FROM accounts WHERE serviceName = serviceName")
    fun getAccountByService(serviceName: String): Flow<Account>
}

