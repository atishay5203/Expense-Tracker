package com.example.expense_tracker.TransactionList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.expense_tracker.R
import com.example.expense_tracker.Transaction
import com.example.expense_tracker.TransactionType
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.sample_item.*
// this is the adapter for the list of transactions used in various fragments
class TransactionListAdapter(private val listner: (Long)->Unit) :
        ListAdapter<Transaction, TransactionListAdapter.TransactionViewHolder>(diffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sample_item, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }


    inner class TransactionViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        init {
           arrow.setOnClickListener {
                listner.invoke(getItem(adapterPosition).id)
            }

        }
// this updates the details of the card view for each transaction
        fun bindView(transaction: Transaction) {
            with(transaction)
            {
             name_sample.text = transaction.name
                amount_sample.text = transaction.amount.toString()
                date_sample.text = transaction.date.toString()
                type_sample.text = TransactionType.values().get(transaction.type).toString()
                if(increment==0)
                {
                    plus_minus.text="-"
                    increment_view.setBackgroundColor(ContextCompat.getColor(increment_view.context, R.color.red))
                    plus_minus.setTextColor(ContextCompat.getColor(plus_minus.context, R.color.red))
                    amount_sample.setTextColor(ContextCompat.getColor(amount_sample.context, R.color.red))


                }
                if(increment==1)
                {
                    plus_minus.text="+"
                    increment_view.setBackgroundColor(ContextCompat.getColor(increment_view.context, R.color.darkgreen))
                    plus_minus.setTextColor(ContextCompat.getColor(plus_minus.context, R.color.darkgreen))
                    amount_sample.setTextColor(ContextCompat.getColor(amount_sample.context, R.color.darkgreen))


                }


            }
        }
    }


    class diffUtilCallBack : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem == newItem
        }

    }
}