package com.github.bassaer.simplemvvm.userlist

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import com.github.bassaer.simplemvvm.data.local.User

class UserlistViewModel: BaseObservable() {
    var users = ObservableArrayList<User>()
    val name = ObservableField<String>()
    val count = ObservableField<Int>()

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