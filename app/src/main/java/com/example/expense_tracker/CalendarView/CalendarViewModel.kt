package com.example.expense_tracker.CalendarView

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.expense_tracker.Transaction
import com.example.expense_tracker.Repositories.TransactionListRepository
// this is a viewmodel for calendar view fragment which will give the transaction list , increments and decrements based on the date selected int the
//calendar
class CalendarViewModel (context: Application): AndroidViewModel(context) {
    private val repo: TransactionListRepository = TransactionListRepository(context)
    private val _date = MutableLiveData<String>()
fun setDate(date:String)
{
   _date.value=date
}
    val list : LiveData<List<Transaction>> =
            Transformations.switchMap(_date)
            {
                date ->
                repo.getTransactionsByDate(date)
            }
    val increment :LiveData<Float> =
            Transformations.switchMap(_date)
            {
                date ->
                repo.getIncrementOnDate(date)
            }
    val decrement :LiveData<Float> =
            Transformations.switchMap(_date)
            {
                date ->
                repo.getDecrementOnDate(date)
            }
}