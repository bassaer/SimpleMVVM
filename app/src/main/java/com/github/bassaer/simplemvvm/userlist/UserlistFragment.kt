package com.github.bassaer.simplemvvm.userlist

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.bassaer.simplemvvm.R
import com.github.bassaer.simplemvvm.data.User
import com.github.bassaer.simplemvvm.databinding.UserItemBinding
import com.github.bassaer.simplemvvm.databinding.UserlistFragBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.ref.WeakReference

class UserlistFragment : Fragment(), NewUserDialogFragment.NoticeDialogListener {

    lateinit var viewModel: UserlistViewModel
    private lateinit var userlistFragBinding: UserlistFragBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        userlistFragBinding = UserlistFragBinding.inflate(inflater, container, false)
        userlistFragBinding.viewmodel = viewModel
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
        userlistFragBinding.userList.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = UserListAdapter(activity as UserlistActivity)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_reset -> {
                viewModel.deleteAllUser()
                return true
            }
            R.id.action_github -> {
                viewModel.openGitHubRepoList()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    class UserListAdapter(private val navigator: UserItemNavigator) :
        RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

        private var userList: List<User> = mutableListOf()

        class UserViewHolder(val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
            val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return UserViewHolder(binding)
        }

        override fun getItemCount(): Int = userList.size

        override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
            val viewModel = UserItemViewModel()
            viewModel.navigator = WeakReference(navigator)
            viewModel.userObservable.set(userList[position])
            viewModel.name.set(userList[position].name)
            viewModel.count.set(userList[position].count.toString())
            holder.binding.viewmodel = viewModel
        }

        fun update(users: List<User>) {
            userList = users
            notifyDataSetChanged()
        }

        companion object {
            @JvmStatic
            @BindingAdapter("items")
            fun RecyclerView.bindItems(users: List<User>) {
                (adapter as UserListAdapter).update(users)
            }
        }
    }

    companion object {
        const val TAG = "USERLIST_FRAGMENT"
        fun newInstance() = UserlistFragment()
    }

}