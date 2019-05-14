package com.github.bassaer.simplemvvm.userlist

import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import com.github.bassaer.simplemvvm.data.local.User

class UserlistViewModel {
    val users = ObservableArrayList<User>()

    var navigator: UserlistNavigator? = null

    fun loadUserlist() {

    }

    /**
     * Called by the Data Binding library when the fab is clicked.
     */
    fun addNewUser() {
        navigator?.addNewUser()
    }

    fun onActivityDestroyed() {
        navigator = null
    }

    @Bindable
    fun isEmpty() = users.isEmpty()
}