package com.github.bassaer.simplemvvm.userlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.bassaer.simplemvvm.R
import com.github.bassaer.simplemvvm.databinding.UserlistFragBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class UserlistFragment : Fragment(), NewUserDialogFragment.NoticeDialogListener {

    lateinit var viewModel: UserlistViewModel
    private lateinit var userlistFragBinding: UserlistFragBinding

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

    override fun onResume() {
        super.onResume()
        viewModel.loadUserlist()
    }

    private fun setupFab() {
        val fab = activity?.findViewById<FloatingActionButton>(R.id.new_user_fab)
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
        viewModel.addNewUser(input)
    }

    private fun setupListAdapter() {
        val recycleListView = userlistFragBinding.userList
        recycleListView.adapter = UserListAdapter()
    }

    companion object {
        const val TAG = "USERLIST_FRAGMENT"
        fun newInstance() = UserlistFragment()
    }

}