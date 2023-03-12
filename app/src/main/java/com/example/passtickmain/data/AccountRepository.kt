package com.example.passtickmain.data
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    //Retrieve all Accounts from the data source//
    fun getAllAccountStream(): Flow<List<Account>>

    // Retrieve an Account from the given data source that matches with the ID

    fun getAccountStream(id: Int):Flow<Account>

    //Insert Account in the data source

    suspend fun insertAccount(account: Account)

    //Delete account from the data source

    suspend fun deleteAccount(account:Account)

    //update account in the data source

    suspend fun updateAccount(account: Account)
}