package com.example.expense_tracker.ProfileViewAndSetup

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.navigation.fragment.findNavController
import com.example.expense_tracker.R
import kotlinx.android.synthetic.main.fragment_set_up_profile.*

// once user enters the app , this page will pop up asking the user to fill the required details
class SetUpProfileFragment : Fragment() {
private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferences2: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // shared preferences have been used to store the credentials of the user
      sharedPreferences=
                requireContext().getSharedPreferences("Credentials", Context.MODE_PRIVATE)
        // once the button is clicked it will be checked if either of the fields is null , else the required details are stored.
        set_profile.setOnClickListener {
            if (namefield.isEmpty()) {
                set_profile.isEnabled = false
                namefield.error = "The field is Empty"
            }
            if (budgetfield.isEmpty()) {
                set_profile.isEnabled = false
                budgetfield.error = "The field is Empty"
            } else {
                namefield.error = null
                budgetfield.error = null
                set_profile.isEnabled = true
            }
            val Username = namefield.editText?.text.toString()
            val Budget = budgetfield.editText?.text.toString()
            if (Username == "" || Budget == "") {
                Toast.makeText(requireContext(), "The * Field are Compulsory to be filled", Toast.LENGTH_SHORT).show();
            } else {
                var income = "Not Yet Provided"
                if (!incomefield.isEmpty()) {
                    income = incomefield.editText?.text.toString()
                }

                with(sharedPreferences.edit())
                {
                    putString("UserName", Username)
                    putString("Budget", Budget)
                    putString("Income", income)
                    apply()
                }
                sharedPreferences2 =
                        requireContext().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE)
                // if the user details are succesfully filled then the isuser is made true indicating that the user is registered with the app

                with(sharedPreferences2.edit())
                {
                    this.putBoolean("isUser", true)
                    commit()
                }
                findNavController().navigate(SetUpProfileFragmentDirections.actionSetUpProfileFragmentToTransactionListFragment())
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_set_up_profile, container, false)
    }

}