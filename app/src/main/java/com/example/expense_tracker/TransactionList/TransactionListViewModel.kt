package com.example.expense_tracker.TransactionList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.expense_tracker.Transaction
import com.example.expense_tracker.Repositories.TransactionListRepository
// this is the viewmodel for transaction list fragment
class TransactionListViewModel (context: Application): AndroidViewModel(context) {
        val repo: TransactionListRepository= TransactionListRepository(context)

    val missedTransaction:LiveData<List<Transaction>> =
            repo.getMissedTransactions()
        val balance:LiveData<Float>  = repo.getTotalAmount()
    val cash : LiveData<Float> = repo.getTotalCash()
    val credit : LiveData<Float> = repo.getTotalCredit()
    val debit : LiveData<Float> = repo.getTotalDebit()
    val sortType:MutableLiveData<Int> = MutableLiveData<Int>(3)
    val transType:MutableLiveData<Int> = MutableLiveData<Int>(0)

    var ListTransactions : LiveData<List<Transaction>> =
            Transformations.switchMap(sortType)
            { st ->
                if (st == 0) {
                    if (transType.value == 0)
                        repo.getUpcomingTransactions()
                    else
                        repo.getMissedTransactions()
                } else {
                    if(transType.value==0)
                        repo.getUpcomingTransactionsByPriority(st-1)
                    else
                        repo.getMissedTransactionsByPriority(st-1)
                }
            }
    fun changeTransType( srt:Int)
    {

        transType.value=srt;
      
         if (srt == 0) {
             if (sortType.value == 0)
               ListTransactions=  repo.getUpcomingTransactions()
             else
                 repo.getUpcomingTransactionsByPriority(sortType.value!!-1)
         } else {
             if(sortType.value==0)
                 ListTransactions=   repo.getMissedTransactions()
             else
                 ListTransactions=   repo.getMissedTransactionsByPriority(sortType.value!!-1)
         }
     }
    fun changeSortType( srt:Int)
    {

        sortType.value=srt;
        if (srt == 0) {
            if (transType.value == 0)
                ListTransactions=    repo.getUpcomingTransactions()
            else
                ListTransactions=   repo.getMissedTransactions()
        } else {
            if(transType.value==0)
                ListTransactions=    repo.getUpcomingTransactionsByPriority(srt-1)
            else
                ListTransactions=    repo.getMissedTransactionsByPriority(srt-1)
        }

    }

    }



