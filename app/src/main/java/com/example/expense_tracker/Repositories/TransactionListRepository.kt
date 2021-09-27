package com.example.expense_tracker.Repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.expense_tracker.Daos.TransactionDatabase
import com.example.expense_tracker.Daos.TransactionListDao
import com.example.expense_tracker.Transaction
import com.example.expense_tracker.models.Expense
import com.example.expense_tracker.models.MonthlyTransactions
//as the view model does not interact with the daos directly so this repository is used replicating all the functions of transaction list dao
// all the respective functions are explained in the transaction list dao
class TransactionListRepository(context: Context) {
    val transactionListDao:TransactionListDao= TransactionDatabase.getDatabase(context).transactionListDao()

    fun getUpcomingTransactions(): LiveData<List<Transaction>> {
        return transactionListDao.getUpcomingTransactions()
    }
    fun getUpcomingTransactionsByPriority(type:Int) :LiveData<List<Transaction>>
    {
        return transactionListDao.getUpcomingTransactionsByPriority(type)
    }
    fun getMissedTransactions():LiveData<List<Transaction>>
    {
        return transactionListDao.getMissedTransactions()
    }
    fun getMissedTransactionsByPriority(type:Int) :LiveData<List<Transaction>>
    {
        return transactionListDao.getMissedTransactionsByPriority(type)
    }
    fun getTransactionsByDate(date:String) :LiveData<List<Transaction>> {
        return transactionListDao.getTransactionByDate(date)
    }

   fun getTotalAmountOnDate(date: String):LiveData<Float>
    {
        return transactionListDao.getTotalAmountOnDate(date)
    }
    fun getIncrementOnDate(date: String):LiveData<Float>
    {
        return  transactionListDao.getIncrementOnDate(date)
    }
    fun getDecrementOnDate(date: String):LiveData<Float>
    {
        return  transactionListDao.getDecrementOnDate(date)
    }

    fun getTotalAmount():LiveData<Float>
    {
        return transactionListDao.getTotalAmount()
    }
    fun getTotalCash():LiveData<Float>
    {
        return transactionListDao.getTotalCash()
    }

    fun getTotalCredit():LiveData<Float>
    {
        return transactionListDao.getTotalCredit()
    }

    fun getTotalDebit():LiveData<Float>
    {
        return transactionListDao.getTotalDebit()
    }
    fun getMonthlyTransactions(monthYear:Long):LiveData<List<Transaction>>
    {
     return  transactionListDao.getMonthlyTransactions(monthYear)
    }
    fun getAmountMonthly(monthYear: Long):LiveData<Float>
    {
        return transactionListDao.getAmountMonthly(monthYear)
    }
    fun getAmountSpentMonthly(monthYear: Long):LiveData<Float>
    {
        return  transactionListDao.getAmountSpentMonthly(monthYear)
    }
    fun getAmountSavedMonthly(monthYear: Long):LiveData<Float>
    {
        return  transactionListDao.getAmountSavedMonthly(monthYear)
    }
    fun getMonthlyExpense(date: String):LiveData<List<Expense>>
    {
        return  transactionListDao.getMonthlyExpense(date)
    }
    fun getMonthlyLists(): LiveData<List<MonthlyTransactions>>
    {
        return  transactionListDao.getTransactionsMonthly()
    }


}