package com.example.expense_tracker.Daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.expense_tracker.Transaction
// Transaction Detail dao will provide the transaction called by the id , once a user clicks on a certain transaction
@Dao
interface TransactionDetailDao {
@Query("SELECT * FROM transactions WHERE `id`=:id")
fun getTransactionById(id:Long):LiveData<Transaction>

@Insert(onConflict = OnConflictStrategy.IGNORE)
suspend fun insertTransaction(transaction: Transaction):Long

@Update
suspend fun updateTransaction(transaction: Transaction)

@Delete
suspend fun deleteTransaction(transaction: Transaction)
// suspend is added as the return type of these functions is not a live data
}