package com.example.passtickmain.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.example.passtickmain.data.*


@Dao //Data Access Object
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAccount(account: Account)

    @Update
    fun  updateAccount(account: Account)

    @Delete
    fun deleteAccount(account: Account)

    @Query("SELECT * FROM accounts")
    fun getAllAccounts(): List<Account>

    @Query("SELECT * FROM accounts WHERE id = accountId")
    fun getAccountbyId(id: Int): Account

    @Query("SELECT * FROM accounts WHERE userName = :userName")
    fun getAccount(userName: String): Account?
}

