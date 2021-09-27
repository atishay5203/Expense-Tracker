package com.example.expense_tracker.MonthlyList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.expense_tracker.models.MonthlyTransactions
import com.example.expense_tracker.Repositories.TransactionListRepository
// this view model holds the monthly transactions list
class MonthlyListViewModel (context: Application): AndroidViewModel(context) {
    private val repo: TransactionListRepository = TransactionListRepository(context)
    val monthlycard: LiveData<List<MonthlyTransactions>>
    get() = repo.getMonthlyLists()

}