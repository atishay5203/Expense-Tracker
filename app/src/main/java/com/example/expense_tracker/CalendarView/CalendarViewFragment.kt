package com.example.expense_tracker.CalendarView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager


import com.example.expense_tracker.R
import com.example.expense_tracker.TransactionList.TransactionListAdapter
import kotlinx.android.synthetic.main.fragment_calendar_view.*


//this fragment is to create a calendar view where the user can select a particular date and get its transactions and net income and expenses
class CalendarViewFragment : Fragment() {
private lateinit var viewModel: CalendarViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel= ViewModelProvider(this).get(CalendarViewModel::class.java)
        super.onCreate(savedInstanceState)

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //the navigation of the app bar(->) will drive the user back to list page
        appBarCalendar.setNavigationOnClickListener {
           findNavController().navigate(CalendarViewFragmentDirections.actionCalendarViewFragmentToTransactionListFragment())

        }
        // this will handle the date changes and give the details accordingly
        calendarView.setOnDateChangeListener { calendarView, i: Int, i1: Int, i2: Int ->
//            i1 += 1
            val month = i1 + 1

            val date: String = if (month < 10) {
                "$i-0$month-$i2"
            } else {
                "$i-$month-$i2"
            }
            // in viewmodel the selected date is updated
            viewModel.setDate(date)

            val mon = getMonth(month)
            "Transactions On $i2 $mon, $i".also { cal_date.text = it }
            // based on the date selected, transaction list is updated
            with(CalendarList) {
                layoutManager = LinearLayoutManager(activity)
                adapter = TransactionListAdapter {
                    findNavController().navigate(CalendarViewFragmentDirections.actionCalendarViewFragmentToTransactionDetail(it))
                }
            }

            viewModel.list.observe(viewLifecycleOwner, {
                (CalendarList.adapter as TransactionListAdapter).submitList(it)
            })

            // the increments and decrements on that date are updated accordingly
            viewModel.increment.observe(viewLifecycleOwner, {
                if (it != null)
                    net_credit.text = it.toString()
                else
                    net_credit.text = getString(R.string.rt)
            })
            viewModel.decrement.observe(viewLifecycleOwner,  {
                if (it != null)
                    net_debit.text = it.toString()
                else
                    net_debit.text =  getString(R.string.rt)
            })
        }
    }


    private fun getMonth(month:Int):String
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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar_view, container, false)
    }

    }
