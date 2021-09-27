package com.example.expense_tracker.Daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.expense_tracker.Transaction
import com.example.expense_tracker.models.Expense
import com.example.expense_tracker.models.MonthlyTransactions

@Dao
interface TransactionListDao {
   //gives the list of sll transactions on a specific date
   @Query("SELECT * FROM transactions WHERE date=:date")
   fun getTransactionByDate(date: String): LiveData<List<Transaction>>
   @Query("SELECT * FROM transactions WHERE date<date('now') AND isCompleted=0")
   fun getMissedTransactions(): LiveData<List<Transaction>>
   // gives the total amount on a particular date
   @Query("SELECT Sum(amount) FROM transactions WHERE date=:date  OR (recurring=1 AND fromDate<=:date AND toDate>=:date)")
   fun getTotalAmountOnDate(date:String): LiveData<Float>

   // gives the increment on that day
   @Query("SELECT Sum(amount) FROM transactions WHERE date=:date OR (recurring=1 AND fromDate<=:date AND toDate>=:date)AND increment = 1")
   fun getIncrementOnDate(date:String): LiveData<Float>

   // gives the decrement on the date
   @Query("SELECT Sum(amount) FROM transactions WHERE date=:date  OR (recurring=1 AND fromDate<=:date AND toDate>=:date) AND increment = 0")
   fun getDecrementOnDate(date:String): LiveData<Float>

   //gives upcoming Transactions
   @Query("SELECT * FROM transactions WHERE (date >=date('now'))  AND isCompleted=0 OR(recurring=1 AND fromDate>=date('now')AND toDate<=date('now')AND isCompleted=0) ORDER BY date DESC")
   fun getUpcomingTransactions() :LiveData<List<Transaction>>

   // gives the upcoming transactions based on their type
   @Query("SELECT * FROM transactions WHERE (date >=date('now'))  AND isCompleted=0 AND type=:type ORDER BY date DESC")
   fun getUpcomingTransactionsByPriority(type:Int) :LiveData<List<Transaction>>

   // gives the missed transactions by priority(cash,credit,debit)
   @Query("SELECT * FROM transactions WHERE (date <date('now'))  AND isCompleted=0 AND type=:type ORDER BY date DESC")
   fun getMissedTransactionsByPriority(type:Int) :LiveData<List<Transaction>>

   //gives net balance
   @Query("SELECT  Sum(amount) FROM transactions")
   fun getTotalAmount():LiveData<Float>

   //gives cash balance
   @Query("SELECT Sum(amount) FROM transactions WHERE type = 0")
   fun getTotalCash():LiveData<Float>

   // gives credit balance
   @Query("SELECT Sum(amount) FROM transactions WHERE type = 1")
   fun getTotalCredit():LiveData<Float>

   //gives debit balance
   @Query("SELECT Sum(amount) FROM transactions WHERE type = 2")
   fun getTotalDebit():LiveData<Float>

   // gives expense for month(for card view of months)
   @Query("SELECT name ,amount, date FROM transactions WHERE date=:date")
   fun getMonthlyExpense(date:String) :LiveData<List<Expense>>

   //get monthly transactions (for monthly detail)
   @Query("SELECT * from transactions WHERE monthYear=:monthYear ORDER BY date")
   fun getMonthlyTransactions(monthYear: Long):LiveData<List<Transaction>>

   // get monthly total
   @Query("SELECT Sum(amount) FROM transactions where monthYear=:monthYear")
   fun getAmountMonthly(monthYear:Long):LiveData<Float>

   //get amount spent monthly
   @Query("SELECT Sum(amount) FROM transactions where monthYear=:monthYear AND increment=0")
   fun getAmountSpentMonthly(monthYear:Long):LiveData<Float>

   // get the amount saved monthly
   @Query("SELECT Sum(amount) FROM transactions where monthYear=:monthYear AND increment=1")
   fun getAmountSavedMonthly(monthYear:Long):LiveData<Float>



//getMonthly saved

   @Query("SELECT t1.monthYear, t1.month, t1.year, SUM(t1.amount) as sum,(SELECT t2.name FROM transactions as t2 WHERE t1.monthYear = t2.monthYear LIMIT 3) as expense FROM transactions as t1 GROUP BY monthYear ORDER BY year, month")
   fun getTransactionsMonthly(): LiveData<List<MonthlyTransactions>>



}