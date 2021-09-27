package com.example.expense_tracker.MonthlyList

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
import kotlinx.android.synthetic.main.fragment_monthly_list.*

//  this fragment holds the cards of various months
class MonthlyListFragment : Fragment() {
       private lateinit var monthlyViewModel : MonthlyListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        monthlyViewModel= ViewModelProvider(this).get(MonthlyListViewModel::class.java)
        return inflater.inflate(R.layout.fragment_monthly_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        appbarmonthly.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                //if the user wants to see the profile
                R.id.profile -> {
                    findNavController().navigate(MonthlyListFragmentDirections.actionMonthlyListFragmentToUserProfileFragment())
                    true

                }
                R.id.calendar -> {
                    // if the user wants to check the calendar view
                    findNavController().navigate(MonthlyListFragmentDirections.actionMonthlyListFragmentToCalendarViewFragment())
                    true
                }
                else ->
                    false

            }

        }
        monthlylistbottom.setOnItemSelectedListener{
               menuItem ->
            when(menuItem.itemId)
            {
                // if the user wants to check the transaction list page
                R.id.dailyView ->
                {
                    findNavController().navigate(MonthlyListFragmentDirections.actionMonthlyListFragmentToTransactionListFragment())
                    true
                }
                // if the user wants to add a new transaction
                R.id.addExpense ->
                {
                    findNavController().navigate(MonthlyListFragmentDirections.actionMonthlyListFragmentToTransactionDetail(0))
                    true
                }
                R.id.monthlyView ->
                    true
                else ->
                    false

            }
        }
        //layout for the monthly cards is setup

        with(monthylist)
        {
            layoutManager= LinearLayoutManager(activity)
            adapter= MonthlyListAdapter({
                findNavController().navigate(MonthlyListFragmentDirections.actionMonthlyListFragmentToMonthlyDetailFragment(it))

            },requireContext())
           monthlyViewModel.monthlycard.observe(viewLifecycleOwner, Observer {
                (monthylist.adapter as MonthlyListAdapter).submitList(it)
            })

        }
    }

}