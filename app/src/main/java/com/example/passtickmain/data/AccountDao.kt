package com.example.passtickmain.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao //Data Access Object
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(account: Account)

    @Update
    fun  update(account: Account)

    @Delete
    fun delete(account: Account)

    @Query("SELECT * FROM accounts")
    fun getAllAccounts(): <List<Account>>

    @Query("SELECT * FROM accounts WHERE id = accountId")
    fun getAccount(accountId: Int): Flow<Account>
}

