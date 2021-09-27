package com.example.expense_tracker.ProfileViewAndSetup

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.example.expense_tracker.R
import kotlinx.android.synthetic.main.fragment_user_profile.*

// this fragment provides the user details
class UserProfileFragment : Fragment() {
private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
// the credentials were stored in set up profile fragment and and user details are updated as per those credentials
        super.onActivityCreated(savedInstanceState)
        sharedPreferences =
                requireContext().getSharedPreferences("Credentials", Context.MODE_PRIVATE)
        var userName=""
        var budget=""
        var income=""
        with(sharedPreferences)
        {
            userName= this.getString("UserName"," Details Not Provided").toString()
           budget= this.getString("Budget","Details Not Provided").toString()
           income= this.getString("Income","Details Not Provided").toString()

        }
        profileName.editText?.setText(userName)
        profileBudget.editText?.setText(budget)
        profileIncome.editText?.setText(income)
        disablefields()
           userprofilebar.setOnMenuItemClickListener {

               menuItem ->
               when (menuItem.itemId) {
                   // if the user want to edit the details
                   R.id.edit_profile -> {
                       save_profile.setText(R.string.save_details)
                       enablefields()

                       true
                   }
                   else ->
                       false


               }
           }
                   // save profile will save the user details if updated in the shared preference
        save_profile.setOnClickListener {
            userName = profileName.editText?.text.toString()
            budget = profileBudget.editText?.text.toString()
            income = profileIncome.editText?.text.toString()
            with(sharedPreferences.edit())
            {
                putString("UserName", userName)
                putString("Budget", budget)
                putString("Income", income)
                apply()
            }
            findNavController().navigate(UserProfileFragmentDirections.actionUserProfileFragmentToTransactionListFragment())
        }
               }




    private fun disablefields() {
        profileName.isEnabled=false
        profileBudget.isEnabled=false
        profileIncome.isEnabled=false
    }
    private fun enablefields() {
        profileName.isEnabled=true
        profileBudget.isEnabled=true
        profileIncome.isEnabled=true
    }

}