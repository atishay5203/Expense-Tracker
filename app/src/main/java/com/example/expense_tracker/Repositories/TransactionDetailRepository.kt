package com.example.expense_tracker.Repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.expense_tracker.Daos.TransactionDatabase
import com.example.expense_tracker.Daos.TransactionDetailDao
import com.example.expense_tracker.Transaction
//as the view model does not interact with the daos directly so this repository is used replicating all the functions of transaction detail dao
class TransactionDetailRepository(application: Application) {
    val transactionDetailDao: TransactionDetailDao = TransactionDatabase.getDatabase(application).transactionDetailDao()
     fun getTransactionById(id:Long) :LiveData<Transaction>
     {
        return transactionDetailDao.getTransactionById(id)
     }
     suspend fun insertTransaction(transaction: Transaction):Long
     {
         return transactionDetailDao.insertTransaction(transaction)
     }
    suspend fun updateTransaction(transaction: Transaction)
    {
        transactionDetailDao.updateTransaction(transaction )
    }
    suspend fun deleteTransaction(transaction: Transaction)
    {
         transactionDetailDao.deleteTransaction(transaction)
    }
}