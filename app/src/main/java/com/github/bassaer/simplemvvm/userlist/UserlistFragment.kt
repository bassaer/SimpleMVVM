package com.github.bassaer.simplemvvm.userlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.bassaer.simplemvvm.R
import com.github.bassaer.simplemvvm.data.local.User
import com.github.bassaer.simplemvvm.data.local.UserDatabase
import com.github.bassaer.simplemvvm.databinding.UserlistFragBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserlistFragment : Fragment(), NewUserDialogFragment.NoticeDialogListener {

    var viewModel: UserlistViewModel? = null
    private lateinit var userlistFragBinding: UserlistFragBinding
    private lateinit var adapter: UserListAdapter
    private var userlist = mutableListOf<User>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        userlistFragBinding = UserlistFragBinding.inflate(inflater, container, false)
        userlistFragBinding.viewmodel  = viewModel
        setHasOptionsMenu(true)
        return userlistFragBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupFab()
        setupListAdapter()
    }

    private fun setupFab() {
        val fab = activity?.findViewById<FloatingActionButton>(R.id.fab)
        fab?.setOnClickListener {
            activity?.let {
                val dialog = NewUserDialogFragment()
                dialog.setTargetFragment(this, 0)
                dialog.show(it.supportFragmentManager, TAG)
            }
        }
    }

    override fun onClickPositiveButton(input: String) {
        if (input.isEmpty()) {
            Toast.makeText(requireContext(), getText(R.string.ng_message), Toast.LENGTH_SHORT).show()
            return
        }

        viewModel?.addNewUser(input)
        val dao = UserDatabase.getInstance(requireContext()).userDao()
        val user = User(name = input, count = 0)
        GlobalScope.launch(Dispatchers.Main) {
            dao.create(user)
            userlist.add(user)
            adapter.notifyDataSetChanged()
        }
    }

    private fun setupListAdapter() {
        val recycleListView = userlistFragBinding.userList
        adapter = UserListAdapter(userlist)
        recycleListView.adapter = adapter
    }

    companion object {
        const val TAG = "USERLIST_FRAGMENT"
        fun newInstance() = UserlistFragment()
    }

}