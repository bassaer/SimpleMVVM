package com.github.bassaer.simplemvvm.userlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.bassaer.simplemvvm.R
import com.github.bassaer.simplemvvm.data.local.User

class UserListAdapter(var userList: MutableList<User>):
    RecyclerView.Adapter<UserListAdapter.UserViewHolder>(){

    class UserViewHolder(cell: View): RecyclerView.ViewHolder(cell) {
        var userView: TextView = cell.findViewById(R.id.cell_name)
        var countView: TextView = cell.findViewById(R.id.cell_count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val cell = LayoutInflater.from(parent.context).inflate(R.layout.user_cell, parent, false)
        return UserViewHolder(cell)
    }

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.userView.text = userList[position].name
        holder.countView.text = userList[position].count.toString()
    }

    fun update(users: MutableList<User>) {
        userList = users
        notifyDataSetChanged()
    }

    companion object {
        @JvmStatic
        @BindingAdapter("items")
        fun RecyclerView.bindItems(users: MutableList<User>) {
            (adapter as UserListAdapter).update(users)
        }
    }
}
