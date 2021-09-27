package com.example.expense_tracker.MonthlyDetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.expense_tracker.Transaction
import com.example.expense_tracker.Repositories.TransactionListRepository
// this is the view model for monthly detail fragment
class MonthlyDetailViewModel (context: Application): AndroidViewModel(context) {
    val repo: TransactionListRepository= TransactionListRepository(context)
    val _monthYear =MutableLiveData<Long>(0)
    val monthYear :LiveData<Long>
    get() = _monthYear
    val monthTransactions :LiveData<List<Transaction>> =
            Transformations.switchMap(_monthYear)
            {
                repo.getMonthlyTransactions(it)
            }
    fun setmonthYear(my:Long)
    {
       if(_monthYear.value!=my)
       {
           _monthYear.value=my
       }
    }


     val spent :LiveData<Float> =
             Transformations.switchMap(_monthYear)
             {
                 repo.getAmountSpentMonthly(it)
             }
    val saved:LiveData<Float> =
            Transformations.switchMap(_monthYear)
            {
                repo.getAmountSavedMonthly(it)
            }
    val total :LiveData<Float> =
            Transformations.switchMap(_monthYear)
            {
                repo.getAmountMonthly(it)
            }






    


}