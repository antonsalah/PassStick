package com.example.passtickmain.data
import kotlinx.coroutines.flow.Flow
package com.example.passtickmain.data

class OfflineAccountRepository(private val accountDao: AccountDao) : AccountRepository

class OfflineAccountRepository() : AccountRepository{

    override fun getAllAccountStream(): Flow<List<Account>> = accountDao.getAllAccount()

    override fun getAccountStream(id: Int): Flow<Account?> = accountDao.getAccount(id)

    override suspend fun insertAccount(account: Account) = accountDao.insert(account)

    override suspend fun deleteAccount(account: Account) = accountDao.delete(account)

    override suspend fun updateAccount(account: Account) = accountDao.update(account)

}