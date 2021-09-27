package com.example.expense_tracker.TransactionDetail

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isEmpty
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.expense_tracker.*
import kotlinx.android.synthetic.main.fragment_transaction_detail.*
import java.text.SimpleDateFormat
import java.util.*

// this fragment creates , modifies and shows the transaction
class TransactionDetailFragment : Fragment() {
private lateinit var viewModel: TransactionDetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)
        //  date select, from date select and to date select , all the edit texts are transformed into date pickers
        DateSelect.editText?.transformIntoDatePicker(requireContext(), "yyyy-MM-dd")
        DateSelect.editText?.transformIntoDatePicker(requireContext(), "yyyy-MM-dd", Date())

        fromDate.editText?.transformIntoDatePicker(requireContext(), "yyyy-MM-dd")
        fromDate.editText?.transformIntoDatePicker(requireContext(), "yyyy-MM-dd", Date())

        toDate.editText?.transformIntoDatePicker(requireContext(), "yyyy-MM-dd")
        toDate.editText?.transformIntoDatePicker(requireContext(), "yyyy-MM-dd", Date())
  // a recurrimg switch is provided in the layout asking the user if the transaction is recurring or not , if recurring the from date and to date
        // pickers are enabled else disabled
        recurringSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                fromDate.isEnabled = true
                toDate.isEnabled = true
            }
            if (!isChecked) {
                fromDate.isEnabled = false
                toDate.isEnabled = false
                fromDate.editText?.setText("")
                toDate.editText?.setText("")


            }
        }
        // to select whether transaction type is cash , credit od debit , a dropdown spinner is provided
        // the array adapeter is set for that spinner
        val types = mutableListOf<String>()
        TransactionType.values().forEach {
            types.add(it.name)
        }

        val categories = mutableListOf<String>()
        TransactionCategory.values().forEach {
            categories.add(it.name)
        }
        val typeAdapter = ArrayAdapter(this.requireActivity(), R.layout.support_simple_spinner_dropdown_item, types)
        // to select transaction category another dropdown spinner is provided
        // the array adapeter is set for that spinner
        val categoryAdapter = ArrayAdapter(this.requireActivity(), R.layout.support_simple_spinner_dropdown_item, categories)
        transactionType.adapter = typeAdapter
        transactionCategory.adapter = categoryAdapter

        transactionType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
        transactionCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
        // the transaction detail viewModel is formed which will provide the transaction details as per the id
        viewModel = ViewModelProvider(this).get(TransactionDetailViewModel::class.java)
        val id = TransactionDetailFragmentArgs.fromBundle(requireArguments()).id
        viewModel.setTransactionId(id)
        if (id == 0L) {
            //if the user forms a new transaction then mark complete button is disabled and rest of the fields are enabled
            markComplete.isVisible=false
            markComplete.isEnabled = false
            enableFields()
        } else if (id != 0L) {
            //if the user forms a new transaction then mark complete button is enabled and rest of the fields are disabled
            disableFields()
            markComplete.isVisible=true
            markComplete.isEnabled = true
            markComplete.setOnClickListener {
                // if the user marks the transaction as completed then its details are uodated accordingly
                saveTransaction(viewModel.transaction.value!!.increment, 1)
                // a dialog box will apppear showing user that transaction is marked as completed
                val builder = AlertDialog.Builder(requireContext())
                val inflater = this.layoutInflater

                val dialogView: View = inflater.inflate(R.layout.markcompletelayout, null)
                with(builder)
                {
                    setView(dialogView)
                    setTitle("Transaction Completed")
                    setCancelable(false)
                    setPositiveButton(
                            "Continue"
                    )
                    { dialog, id ->

                    }

                }
                val dialog= builder.create()
                dialog.show()
                // after the dialog box is clicked ok , user will be directed back to list fragment
                this.requireActivity().onBackPressed()

            }
        }
        // if the user views a transaction , then its details are updated accordingly
        viewModel.transaction.observe(viewLifecycleOwner,
                {
                    it?.let { loadData(it) }
                })




        appBarLayout1.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.delete -> {
                    // if the user wants to delete the transaction
                    deleteTransaction()
                    requireActivity().onBackPressed()
                    true
                }
                R.id.edit_transaction -> {
                    // if the user wants to edit a transaction
                    enableFields()
                    // if the transaction is an income , then the increment is 1
                    income.setOnClickListener {

                        saveTransaction(1,0)


                    }
                    // if the transaction is an expense then the increment is 0
                    expense.setOnClickListener {
                        saveTransaction(0,0)

                    }
                    true

                }

                else ->
                    false
            }
        }
            income.setOnClickListener {
                saveTransaction(1,0)


            }
        expense.setOnClickListener { 
            saveTransaction(0,0)

        }
        




    }
    // once user clicks income or expense button the transaction details will be saved accordingly
    private fun saveTransaction(increment: Int,isCompleted:Int) {

       val name= TransactionName.editText?.text.toString()
        val amount= (TransactionAmount.editText?.text.toString())
        val date = DateSelect.editText?.text.toString()
        if(amount==""||name==""||date=="")

        {
            Toast.makeText(requireContext(), "The * Field are Compulsory to be filled", Toast.LENGTH_SHORT).show();
        }
        else {
            var amount2 = amount.toFloat()
            amount2.let {
                if (increment == 0)
                    amount2 *= -1
            }
            val fromDates = fromDate.editText?.text.toString()
            val toDates = toDate.editText?.text.toString()
            val detail = transactionDetails.text.toString()
            val type = transactionType.selectedItemPosition
            val category = transactionCategory.selectedItem.toString()

            val recurring = if (recurringSwitch.isChecked)
                1
            else
                0
            val year = date.substring(0, 4).toInt()
            val month = date.substring(5, 7).toInt()
            val monthYear = (date.substring(0, 4) + date.substring(5, 7)).toLong()
            val transaction: Transaction = Transaction(viewModel.transactionId.value!!, name, amount2, detail, type, category,
                    increment, isCompleted, recurring, date, fromDates, toDates, monthYear, month, year)
            viewModel.saveTransaction(transaction)
            val builder = AlertDialog.Builder(requireContext())
            val inflater = this.layoutInflater
            val dialogView: View = inflater.inflate(R.layout.saved_succesfully, null)
            with(builder)
            {
                setView(dialogView)
                setTitle("Transaction Details Saved")
                setCancelable(false)
                setPositiveButton(
                        "Continue"
                )
                { dialog, id ->

                }

            }
            val dialog = builder.create()
            dialog.show()
            requireActivity().onBackPressed()
        }




    }
    // this function is to load the data
private fun loadData(transaction:Transaction)
{
    TransactionName.editText?.setText(transaction.name)
    TransactionAmount.editText?.setText(transaction.amount.toString())
    DateSelect.editText?.setText(transaction.date)

    if(transaction.recurring==1) {
        recurringSwitch.isChecked=true;
        fromDate.editText?.setText(transaction.fromDate)
        toDate.editText?.setText(transaction.toDate)
    }
    else
    {
        recurringSwitch.isChecked=false
        fromDate.editText?.setText("")
        toDate.editText?.setText("")
    }
    transactionType.setSelection(transaction.type)
    val ordinal= TransactionCategory.values().find { it.toString() == transaction.category }!!.ordinal
    transactionCategory.setSelection(ordinal)
    if(transaction.isCompleted==1)
    {
        markComplete.isEnabled=false
        markComplete.text = getString(R.string.co)
    }
  if(transaction.increment==1) {
      expense.isVisible = false
      income.isVisible=true
  }
    if(transaction.increment==0) {
        expense.isVisible = true
        income.isVisible=false
    }


}
    private fun deleteTransaction() {
          viewModel.deleteTransaction()
        requireActivity().onBackPressed()
    }

    private fun enableFields() {
        TransactionName.isEnabled=true
        TransactionAmount.isEnabled=true
        DateSelect.isEnabled=true
        fromDate.isEnabled=true
        toDate.isEnabled=true
        transactionType.isEnabled=true
        transactionCategory.isEnabled=true
        transactionDetails.isEnabled=true
        recurringSwitch.isEnabled=true
        income.isVisible=true
        expense.isVisible=true
        income.isEnabled=true
        expense.isEnabled=true
        markComplete.isEnabled=true

    }

    private fun disableFields() {
      TransactionName.isEnabled=false
        TransactionAmount.isEnabled=false
        DateSelect.isEnabled=false
        fromDate.isEnabled=false
        toDate.isEnabled=false
        transactionType.isEnabled=false
        transactionCategory.isEnabled=false
        transactionDetails.isEnabled=false
        recurringSwitch.isEnabled=false
        income.isEnabled=false
        expense.isEnabled=false


    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction_detail, container, false)
    }

    // this is the date picker transformer
    fun EditText.transformIntoDatePicker(context: Context, format: String, maxDate: Date? = null) {
        isFocusableInTouchMode = false
        isClickable = true
        isFocusable = false

        val myCalendar = Calendar.getInstance()
        val datePickerOnDataSetListener =
                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    myCalendar.set(Calendar.YEAR, year)
                    myCalendar.set(Calendar.MONTH, monthOfYear)
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    val sdf = SimpleDateFormat(format, Locale.UK)
                    setText(sdf.format(myCalendar.time))
                }

        setOnClickListener {
            DatePickerDialog(
                    context, datePickerOnDataSetListener, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)
            ).run {

                show()
            }


        }
    }
}