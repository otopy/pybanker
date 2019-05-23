package com.example.pybanker.ui.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.pybanker.model.AccountsAdapter
import com.example.pybanker.model.DBHelper
import com.example.pybanker.R
import kotlinx.android.synthetic.main.frg_accounts.*
import java.lang.Exception


/**
 * A simple [Fragment] subclass.
 *
 */
class FrgAccounts : Fragment() {

    private var dbhelper: DBHelper? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        dbhelper = DBHelper(activity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frg_accounts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnAddAccount.setOnClickListener{
            val fragment = FrgAddAccount()
            activity!!.supportFragmentManager
                .beginTransaction().replace(R.id.frame_layout_main, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val accounts  = ArrayList<Account>()

        val res = dbhelper?.getAccounts

        try {
            while (res!!.moveToNext()) {
                accounts.add(
                    Account(
                        res.getString(0),
                        res.getString(1),
                        "£" + res.getString(2),
                        res.getString(3),
                        res.getString(4)
                    )
                )
            }
        } catch (e: Exception) {
            Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
        }

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_accounts.layoutManager = layoutManager
        rv_accounts.adapter = AccountsAdapter(context, accounts, this.fragmentManager)
        super.onActivityCreated(savedInstanceState)
    }

    data class Account(var accountName: String,
                       var lastOperated: String,
                       var balance: String,
                       var status: String,
                       var type: String)

}
