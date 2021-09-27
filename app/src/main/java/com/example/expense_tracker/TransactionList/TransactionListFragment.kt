package com.example.expense_tracker.TransactionList

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expense_tracker.*
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.fragment_transaction_list.*

// this is the list fragment where net balance of the user along with his upcoming and missed transactions are shown
private lateinit var viewModel: TransactionListViewModel
class TransactionListFragment : Fragment() {
    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TransactionListViewModel::class.java)

     listappbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.profile -> {
                   findNavController().navigate(TransactionListFragmentDirections.actionTransactionListFragmentToUserProfileFragment())
                    true

                }
                R.id.calendar -> {
                   findNavController().navigate(TransactionListFragmentDirections.actionTransactionListFragmentToCalendarViewFragment())
                    true
                }
                else ->
                    false

            }

        }
        listbottom.menu.findItem(R.id.dailyView).setChecked(true)
        listbottom.setOnItemSelectedListener{
            menuItem ->
            when(menuItem.itemId)
            {
                R.id.dailyView ->
                {

                    true
                }
                R.id.addExpense ->
                {
                   findNavController().navigate(TransactionListFragmentDirections.actionTransactionListFragmentToTransactionDetail(0))
                    true
                }
                R.id.monthlyView -> {
                    findNavController().navigate(TransactionListFragmentDirections.actionTransactionListFragmentToMonthlyListFragment())
                    true
                }
                else ->
                    false

            }
        }
        val sortType= mutableListOf<String>()
        SortType.values().forEach {
            sortType.add(it.name)
        }
        val sortAdapter = ArrayAdapter(this.requireActivity(), R.layout.support_simple_spinner_dropdown_item, sortType)

        sortSpinner.adapter = sortAdapter
        sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
              viewModel.changeSortType(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
        val transType= mutableListOf<String>()
       trans.values().forEach {
            transType.add(it.name)
        }
        val transAdapter = ArrayAdapter(this.requireActivity(), R.layout.support_simple_spinner_dropdown_item,transType)

       trans_message.adapter = transAdapter
        trans_message.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.changeTransType(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
        with(missedRecycleView)
        {
            layoutManager = LinearLayoutManager(activity)
            adapter = TransactionListAdapter {
                findNavController().navigate(TransactionListFragmentDirections.actionTransactionListFragmentToTransactionDetail(it))

            }
        }
            viewModel.ListTransactions.observe(viewLifecycleOwner, Observer {
                (missedRecycleView.adapter as TransactionListAdapter).submitList(it)
            })

 with(missedRecycleView)
        {
            layoutManager = LinearLayoutManager(activity)
            adapter = TransactionListAdapter {
                findNavController().navigate(TransactionListFragmentDirections.actionTransactionListFragmentToTransactionDetail(it))

            }
        }
            viewModel.missedTransaction.observe(viewLifecycleOwner, Observer {
                (missedRecycleView.adapter as TransactionListAdapter).submitList(it)
            })


// net balance ,cash and credit details , all are updated accordingly via viewmodel
        viewModel.balance.observe(viewLifecycleOwner, Observer {
            if(it!=null) {
                if(it>=0) {
                    total_bal.text = it.toString()
                    setupPieChart()
                }
                else
                {
                    val bals=it*-1
                    total_bal.text= "Debt Of $bals"
                    total_bal.setTextColor(ContextCompat.getColor(total_bal.context,R.color.red))

                }
            }
            else
                total_bal.text="0.00"


        })
        viewModel.cash.observe(viewLifecycleOwner, Observer {
            if(it!=null) {
                cash_bal.text = it.toString()

            }
            else
            {
                cash_bal.text="0.00"
            }
            viewModel.balance.let {
                if(it.value!=null&&it.value!! >=0)
                    setupPieChart()
            }
        })
        viewModel.credit.observe(viewLifecycleOwner, Observer {
            if(it!=null) {
                credit_bal.text = it.toString()
            }
            else
                credit_bal.text="0.00"
            viewModel.balance.let {
                if(it.value!=null&&it.value!! >=0)
                    setupPieChart()
            }

        })
        viewModel.debit.observe(viewLifecycleOwner, Observer {
            if(it!=null) {
                debit_bal.text = it.toString()
            }
            else
                debit_bal.text="0.00"
            viewModel.balance.let {
                if(it.value!=null&&it.value!! >=0)
                    setupPieChart()
            }
        })

    }
    // this is the function used to set up the pie chart with the entries provided by the user
    private fun setupPieChart() {

        // setup Pie entries
        val pieEntries = arrayListOf<PieEntry>()
        val first: Float = total_bal.text.toString().toFloat()
        val second: Float = cash_bal.text.toString().toFloat()
        val third: Float = credit_bal.text.toString().toFloat()
        val forth: Float = debit_bal.text.toString().toFloat()

        pieEntries.add(PieEntry(first))
        pieEntries.add(PieEntry(second))
        pieEntries.add(PieEntry(third))
        pieEntries.add(PieEntry(forth))
// setup Pie chart animations
        piechart.animateXY(1000, 1000)

        // setup PieChart Entries Colors
        val pieDataSet = PieDataSet(pieEntries, "This is Expense Pie Chart")
        pieDataSet.setColors(
               ContextCompat.getColor(requireContext(),R.color.c1),
                       ContextCompat.getColor(requireContext(),R.color.c2),
                ContextCompat.getColor(requireContext(),R.color.c3),
                ContextCompat.getColor(requireContext(),R.color.c4)

        )

        // setup pie data set in piedata
        val pieData = PieData(pieDataSet)

        // setip text in pieChart centre
        piechart.centerText = "Expenses"
        piechart.setCenterTextColor(resources.getColor(android.R.color.black))
        piechart.setCenterTextSize(15f)

        // hide the piechart entries tags
        piechart.legend.isEnabled = false

//        now hide the description of piechart
        piechart.description.isEnabled = false
        piechart.description.text = "Expanses"

        piechart.holeRadius = 40f
        // this enabled the values on each pieEntry
        pieData.setDrawValues(true)

        piechart.data = pieData
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction_list, container, false)
    }

}