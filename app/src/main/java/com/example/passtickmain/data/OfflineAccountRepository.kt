package com.example.passtickmain.data
import kotlinx.coroutines.flow.Flow

//class OfflineAccountRepository(private val accountDao: AccountDao) : AccountRepository

class OfflineAccountRepository(private val accountDao: AccountDao) : AccountRepository{

    override fun getAllAccountStream(): Flow<List<Account>> = accountDao.getAllAccounts()

    override fun getAccountStream(id: Int): Flow<Account?> = accountDao.getAccount(id)

    override suspend fun insertAccount(account: Account) = accountDao.insert(account)

    override suspend fun deleteAccount(account: Account) = accountDao.delete(account)

    override suspend fun updateAccount(account: Account) = accountDao.update(account)

}