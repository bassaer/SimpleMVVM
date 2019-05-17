package com.github.bassaer.simplemvvm.userlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.bassaer.simplemvvm.R
import com.github.bassaer.simplemvvm.databinding.UserlistFragBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class UserlistFragment : Fragment() {

    var viewModel: UserlistViewModel? = null
    private lateinit var userlistFlagBinding: UserlistFragBinding
    private lateinit var adapter: UserListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        userlistFlagBinding = UserlistFragBinding.inflate(inflater, container, false)
        userlistFlagBinding.viewmodel  = viewModel
        setHasOptionsMenu(true)
        return userlistFlagBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupFab()
        setupListAdapter()
    }

    private fun setupFab() {
        val fab = activity?.findViewById<FloatingActionButton>(R.id.fab)
        fab?.setOnClickListener {
            this.viewModel?.addNewUser()
        }
    }

    private fun setupListAdapter() {
        val recycleListView = userlistFlagBinding.userList
        adapter = UserListAdapter(mutableListOf())
        recycleListView.adapter = adapter
    }

}