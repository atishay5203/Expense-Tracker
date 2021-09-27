package com.example.expense_tracker.MonthlyList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.expense_tracker.R
import com.example.expense_tracker.models.Expense
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.monthly_list_item.*
// monthly card adaptetr is used to set up the recycle list view of the month card where all the expenses of a particular month are shown
class MonthlyCardAdapter (private val expenses :List<Expense>) :
        RecyclerView.Adapter<MonthlyCardAdapter.MonthlyCardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthlyCardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.monthly_list_item, parent, false)
        return MonthlyCardViewHolder(view)
    }

    override fun onBindViewHolder(holder: MonthlyCardViewHolder, position: Int) {
        holder.bindView(expenses[position])
    }


    inner class MonthlyCardViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindView(expense:Expense) {
            monthlyListTitle.text= expense.name
            monthlyListAmount.text= expense.amount.toString()
            if(expense.amount>=0)
            {
                monthlyListPlus.text="+"
                monthlyListAmount.setTextColor(ContextCompat.getColor(monthlyListAmount.context, R.color.darkgreen))
                monthlyListPlus.setTextColor(ContextCompat.getColor(monthlyListAmount.context, R.color.darkgreen))

            }
            else
            {
                monthlyListPlus.text="-"
                monthlyListAmount.setTextColor(ContextCompat.getColor(monthlyListAmount.context, R.color.red))
                monthlyListPlus.setTextColor(ContextCompat.getColor(monthlyListAmount.context, R.color.red))

            }

        }
    }

    override fun getItemCount(): Int {
        return expenses.size
    }


}
