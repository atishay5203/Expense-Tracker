package com.example.expense_tracker.TransactionDetail

import android.app.Application
import androidx.lifecycle.*
import com.example.expense_tracker.Transaction
import com.example.expense_tracker.Repositories.TransactionDetailRepository
import kotlinx.coroutines.launch
// this is the view model for transaction detail fragment
class TransactionDetailViewModel(context:Application):AndroidViewModel(context) {
    val repo: TransactionDetailRepository = TransactionDetailRepository(context)
    private val _transactionId = MutableLiveData<Long>(0)
    val transactionId: LiveData<Long>
        get() = _transactionId
    val transaction: LiveData<Transaction> = Transformations.switchMap(_transactionId)
    {
        repo.getTransactionById(it)

    }

 fun setTransactionId(id: Long) {
        if (_transactionId.value != id) {
            _transactionId.value = id

        }
    }

     fun saveTransaction(transaction: Transaction) {
        viewModelScope.launch {
            if (_transactionId.value == 0L) {
                _transactionId.value = repo.insertTransaction(transaction)
            } else
                repo.updateTransaction(transaction)
        }
    }

    fun deleteTransaction() {
        viewModelScope.launch {
            transaction.value?.let { repo.deleteTransaction(it) }
        }
    }
}