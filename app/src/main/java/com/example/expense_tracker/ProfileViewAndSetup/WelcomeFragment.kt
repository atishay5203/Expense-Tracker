package com.example.expense_tracker.ProfileViewAndSetup

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import com.example.expense_tracker.R
import kotlinx.android.synthetic.main.fragment_welcome.*

class WelcomeFragment : Fragment() {
// this fragment will pop up once the user opens the app for the first time

private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // if the user is already registered with app he will be directed to the transaction list page
        sharedPreferences =
                requireContext().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE)
        val isLoggedIn= sharedPreferences.getBoolean("isUser", false)
        if(isLoggedIn)
        {
            findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToTransactionListFragment())
        }

        // if the user clicks the enter button then he will be directed to set up profile page

       skip.setOnClickListener {
            findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToSetUpProfileFragment())

        }

    }



}


