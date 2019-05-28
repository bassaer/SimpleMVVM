package com.github.bassaer.simplemvvm.userlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.bassaer.simplemvvm.data.local.User
import com.github.bassaer.simplemvvm.databinding.UserItemBinding

class UserListAdapter:
    RecyclerView.Adapter<UserListAdapter.UserViewHolder>(){

    private var userList: List<User> = mutableListOf()

    class UserViewHolder(val binding: UserItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context),  parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val viewModel = UserItemViewModel()
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
            (this.adapter as UserListAdapter).update(users)
        }
    }
}
