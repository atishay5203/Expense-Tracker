package com.example.expense_tracker.MonthlyDetail

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expense_tracker.R
import com.example.expense_tracker.TransactionList.TransactionListAdapter
import kotlinx.android.synthetic.main.fragment_monthly_detail.*

// when the user clicks on the view all button from the monthly cards , then mpnthly detail screen pops up which provides user with the details of the month
class MonthlyDetailFragment : Fragment() {
    private lateinit var viewModel: MonthlyDetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            viewModel = ViewModelProvider(this).get(MonthlyDetailViewModel::class.java)


    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_monthly_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // if user wants to navigate back to month cards page then user may click bacl (<-) button on the app bar which will direct the user back to the month cards
        appbarmonthlydetail.setNavigationOnClickListener {
            findNavController().navigate(MonthlyDetailFragmentDirections.actionMonthlyDetailFragmentToMonthlyListFragment())

        }
        monthlydetailbottom.setOnItemSelectedListener{
            menuItem ->
            when(menuItem.itemId)
            {
                // if user wants to get back to the list view he may select that from the bottom navigation
                R.id.dailyView ->
                {
                    findNavController().navigate(MonthlyDetailFragmentDirections.actionMonthlyDetailFragmentToTransactionListFragment())
                    true
                }
                // if the user wants to create a new transaction , he will be directed to transaction detail page
                R.id.addExpense ->
                {
                    findNavController().navigate(MonthlyDetailFragmentDirections.actionMonthlyDetailFragmentToTransactionDetail(0))
                    true
                }
                R.id.monthlyView ->
                    true
                else ->
                    false

            }
        }
// as per the selected month , the monthyear will be updated in the view model
        val monthYear = MonthlyDetailFragmentArgs.fromBundle(requireArguments()).monthYear
        viewModel.setmonthYear(monthYear)
        // this manages the transaction list view of a particular month , clicking on a particular transaction , user will be directed to transaction detail page
        with(monthlyRecycleView)
        {
            layoutManager=LinearLayoutManager(activity)
            adapter= TransactionListAdapter{
                findNavController().navigate(MonthlyDetailFragmentDirections.actionMonthlyDetailFragmentToTransactionDetail(it))
            }

        }
    viewModel.monthTransactions.observe(viewLifecycleOwner, Observer {
        (monthlyRecycleView.adapter as TransactionListAdapter).submitList(it)
    })
        val sharedPreferences: SharedPreferences =
            requireContext().getSharedPreferences("Credentials", Context.MODE_PRIVATE)

        // the other details of the month are updated accordingly
        val budget= sharedPreferences.getString("Budget","0.0")!!.toDouble()
        viewModel.saved.observe(viewLifecycleOwner, Observer { saved ->


            if(saved!=null) {
                amount_save.text = saved.toString()

            }
            else {
                amount_save.text = getString(R.string.rt)
            }

        })
        viewModel.spent.observe(viewLifecycleOwner, Observer {
            spent ->
            if(spent!=null) {

                amount_spent.text = spent.toString()
            }
            else
                amount_spent.text=getString(R.string.rt)

        })
        val saves= amount_save.text.toString().toFloat()
        val spens= amount_spent.text.toString().toFloat()
      viewModel.total.observe(viewLifecycleOwner, Observer { balance ->

// if the user balance is less than 0 , the message is given to the user that he is in debt
          if(balance!=null) {
              if(balance>=0)
              monthly_bal.text = balance.toString()
              else
                  ("Debt of" + balance*-1).also { monthly_bal.text = it }
          }
              else
                  monthly_bal.text=getString(R.string.rt)

          })

        ratio_monthly.progress= ((saves*100F)/(saves+spens)).toInt()

      viewModel.monthYear.let {
          appbarmonthlydetail.setTitle("Expenses - "+ getMonth(it.value.toString().substring(4,6).toInt()))
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
