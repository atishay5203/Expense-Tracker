package com.example.expense_tracker.MonthlyList

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.expense_tracker.R
import com.example.expense_tracker.models.MonthlyTransactions
import com.example.expense_tracker.Repositories.TransactionListRepository
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_monthly_list_sample.*
// this adapter is to setup various monthly cards
class MonthlyListAdapter(private val listner: (Long)->Unit, val context:Context) :
        ListAdapter<MonthlyTransactions, MonthlyListAdapter.MonthlyViewHolder>(diffUtilCallBack2()) {
private lateinit var repo: TransactionListRepository

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthlyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_monthly_list_sample, parent, false)
        return MonthlyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MonthlyViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }


    inner class MonthlyViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        init {
            // if view_all is clicked , user will be directed to the month details page giving details of the selected month
           view_all.setOnClickListener {
                listner.invoke(getItem(adapterPosition).monthYear)
            }
        }



        fun bindView(monthlyTransactions: MonthlyTransactions) {


// the details of the each month card is updated as per the monthyear
       month.text= getMonth(monthlyTransactions.month)
            year.text= monthlyTransactions.year.toString()
           val monthLayoutManager= LinearLayoutManager(context, RecyclerView.VERTICAL,false )
            monthLayoutManager.initialPrefetchItemCount=4
            with(monthly_list)
            {
          layoutManager=monthLayoutManager
                adapter= MonthlyCardAdapter(monthlyTransactions.expenses)
                // recycled view pool is used since all these month cards are also using monthly list adapters
              setRecycledViewPool(RecyclerView.RecycledViewPool())
            }
            repo = TransactionListRepository(context)
// if the user exceeds the monthly budget set then the message of budget limit exceeded is given to the user
            val currbudget= repo.getAmountMonthly(monthlyTransactions.monthYear)

            val sharedPreferences: SharedPreferences =
           context.getSharedPreferences("Credentials", Context.MODE_PRIVATE)
            val budget= sharedPreferences.getString("Budget","0.0")!!.toFloat()
         currbudget.value.let {
             if(it!=null)
             {
                 if(it*-1F>budget)
                 {
                     exceed_message.isVisible=true
                     exceed_color.setBackgroundColor(ContextCompat.getColor(context,R.color.red))
                 }
                 else
                 {
                     exceed_message.isVisible=false
                     exceed_color.setBackgroundColor(ContextCompat.getColor(context,R.color.green))
                 }
             }
         }



        }
    }


    class diffUtilCallBack2 : DiffUtil.ItemCallback<MonthlyTransactions>() {
        override fun areItemsTheSame(oldItem: MonthlyTransactions, newItem: MonthlyTransactions): Boolean {
            return oldItem.monthYear == newItem.monthYear
        }

        override fun areContentsTheSame(oldItem: MonthlyTransactions, newItem: MonthlyTransactions): Boolean {
            return oldItem == newItem
        }

    }
    fun getMonth(month:Int):String
    {
        when(month)
        {
            1 -> return "January"
            2 -> return "February"
            3 -> return "March"
            4 -> return "April"
            5 -> return "May"
            6 -> return "June"
            7 -> return "July"
            8 -> return "August"
            9 -> return "September"
            10 -> return "October"
            11 -> return "November"
            12 -> return "December"

        }

        return "null"
    }
}